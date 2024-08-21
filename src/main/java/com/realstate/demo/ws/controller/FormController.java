//package com.realstate.demo.ws.controller;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import rastak.train.ws.model.dto.FormDto;
//import rastak.train.ws.service.FormService;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/form")
//public class FormController {
//
//    @Autowired
//    private final FormService formService;
//
//    public FormController(FormService formService) {
//        this.formService = formService;
//    }
//
//    @PostMapping("/create")
//    public String createForm(@RequestBody Map<String, List<String>> fieldData) {
//        formService.createForm(fieldData);
//        return "Form created successfully";
//    }
//
//    @GetMapping("/all")
//    public List<FormDto> getAllForms() {
//        return formService.getAllForms();
//    }
//}