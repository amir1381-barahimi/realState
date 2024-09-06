    package com.realstate.demo.ws.service.impl;

    import com.realstate.demo.ws.model.entity.Advertisement;
    import com.realstate.demo.ws.model.entity.UserEntity;
    import com.realstate.demo.ws.model.response.JSONAdvertisementResponse;
    import com.realstate.demo.ws.repository.AdRepository;
    import com.realstate.demo.ws.service.AdService;
    import com.realstate.demo.ws.util.AdUtils;
    import com.realstate.demo.ws.util.UserUtils;
    import jakarta.servlet.http.HttpServletRequest;
    import org.springframework.stereotype.Service;
    import org.springframework.util.StringUtils;


    import java.util.List;
    import java.util.Optional;

    @Service
    public class AdServiceImpl implements AdService {

        private final AdUtils utils;
        private final UserUtils userUtils;
        private final AdRepository adRepository;

        public AdServiceImpl(AdUtils utils, UserUtils userUtils, AdRepository adRepository) {
            this.utils = utils;
            this.userUtils = userUtils;
            this.adRepository = adRepository;
        }

    //    @Override
    //    public JSONAdvertisementResponse createAd(String title, String description, long homeId, int contactNumber, ListingStatus listingStatus, PropertyStatus propertyStatus, HttpServletRequest request) {
    //        UserEntity user = userUtils.getCurrentUser(request);
    //        Advertisement i = utils.convert(title, description, homeId, contactNumber, listingStatus, propertyStatus);
    //        i.setUser(user);
    //        adRepository.save(i);
    //        return utils.convert(i);
    //    }

        @Override
        public JSONAdvertisementResponse createAd(String title, String description, String street, String city, String postalCode, String country, String statePrice, String stateType, String numberBath, String numberBed, String email, String phone,String image,String numberParking,String latitude , String longitude, HttpServletRequest request) {
            UserEntity user = userUtils.getCurrentUser(request);
            Advertisement i = utils.convert(title, description, street, city, postalCode, country, statePrice, stateType, numberBath, numberBed, email, phone,image,numberParking,latitude,longitude, user);
            adRepository.save(i);

            return utils.convert(i, null);
        }

        @Override
        public JSONAdvertisementResponse getAd(long id) {
            Advertisement i = adRepository.findAdById(id);
            Advertisement ad = findClosestHome(id);
            Optional<Long> adId = Optional.of(ad.getId());
            if (adId.isPresent()) {
                long adId1 = adId.get();
                return utils.convert(i, adId1);
            }
            return null;
        }

        @Override
        public List<JSONAdvertisementResponse> getAllAdd(String city) {
            if (StringUtils.hasText(city)){
                List<Advertisement> jlist = adRepository.findAdByCity(city);
                return utils.convert(jlist);
            }
            List<Advertisement> list = adRepository.findAll();
            return utils.convert(list);
        }

        @Override
        public void deleteAd(long id) {
            adRepository.deleteById(id);
        }

        @Override
        public List<JSONAdvertisementResponse> getAllAdByUser(HttpServletRequest request) {
            UserEntity user = userUtils.getCurrentUser(request);
            List<Advertisement> list = adRepository.getAllAdByUserId(user.getId());
            return utils.convert(list);
        }




        @Override
        public Advertisement findClosestHome(Long id) {

            Optional<Advertisement> optionalAdvertisement = Optional.ofNullable(adRepository.findAdById(id));

            if (optionalAdvertisement.isPresent()) {
                Advertisement currentHome = optionalAdvertisement.get();

                List<Advertisement> allAd = adRepository.findAll();

                Advertisement closestHome = null;
                double closestDistance = Double.MAX_VALUE;

                for (Advertisement ad : allAd) {
                    if (ad.getId() != currentHome.getId()) {
                        double distance = calculateDistance(currentHome, ad);

                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestHome = ad;
                        }
                    }
                }
                return closestHome;
            }
            return null;
        }

        private double calculateDistance(Advertisement advertisement1, Advertisement advertisement2) {
            double distance = 0;

            distance += advertisement1.getCity().equals(advertisement2.getCity()) ? 0 : 3;
            distance += advertisement1.getStreet().equals(advertisement2.getStreet()) ? 0 : 1   ;

            double price1 = Double.parseDouble(advertisement1.getStatePrice());
            double price2 = Double.parseDouble(advertisement2.getStatePrice());
            distance += Math.abs(price1 - price2);

            double bed1 = Double.parseDouble(advertisement1.getNumberBed());
            double bed2 = Double.parseDouble(advertisement2.getNumberBed());
            distance += Math.abs(bed1 - bed2);


            double bath1 = Double.parseDouble(advertisement1.getNumberBath());
            double bath2 = Double.parseDouble(advertisement2.getNumberBath());
            distance += Math.abs(bath1 - bath2);

            return distance;
        }
    }
