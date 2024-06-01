package rastak.train.ws.service.impl;

import net.formio.FormMapping;
import net.formio.Forms;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import rastak.train.shared.TicketException;
import rastak.train.ws.model.dto.FormDto;
import rastak.train.ws.model.entity.FormEntity;
import rastak.train.ws.model.request.DynamicForm;
import rastak.train.ws.repository.FormRepository;
import rastak.train.ws.service.FormService;

import java.util.List;
import java.util.Map;

@Service
public class FormServiceImpl implements FormService {

    private final FormRepository formRepository;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public FormServiceImpl(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    @Override
    public void createForm(Map<String, List<String>> fieldData) {
        List<String> fields = fieldData.get("fields");

        if (fields != null && !fields.isEmpty()) {
            try {
                FormMapping<DynamicForm> dynamicForm = Forms.basic(DynamicForm.class, "dynamicForm")
                        .fields(fields.toArray(new String[0]))
                        .build();

                FormEntity formEntity = new FormEntity();
                formRepository.save(formEntity);
                logger.info("form created");
                System.out.println("Form fields: " + fields);
            } catch (Exception e) {
                System.err.println("Error while creating and saving form: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No fields provided to create the form.");
        }
    }

    @Override
    public List<FormDto> getAllForms() {
        List<FormEntity> forms = formRepository.findAll();
        if (forms == null) {
            throw new TicketException("Any ticket not found", HttpStatus.NOT_FOUND);
        }
        return forms.stream().map(form -> new ModelMapper().map(form, FormDto.class)).toList();
    }
}
