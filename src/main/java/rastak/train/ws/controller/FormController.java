package rastak.train.ws.controller;

import net.formio.FormMapping;
import net.formio.Forms;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rastak.train.ws.model.request.DynamicForm;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/form")
public class FormController {
    @PostMapping("/create")
    public void createForm(@RequestBody Map<String, List<String>> fieldData) {
        List<String> fields = fieldData.get("fields");

        if (fields != null && !fields.isEmpty()) {
            FormMapping<DynamicForm> dynamicForm = Forms.basic(DynamicForm.class, "dynamicForm")
                    .fields(fields.toArray(new String[0]))
                    .build();
            System.out.println("Form fields: " + fields);
        } else {
            System.out.println("No fields provided to create the form.");
        }
    }
}