package pl.tau.phoneapp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.tau.phoneapp.domain.Phone;
import pl.tau.phoneapp.service.PhoneManager;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@RestController
public class PhoneController {

    @Autowired
    PhoneManager libraryManager;

    @RequestMapping("/phones")
    public java.util.List<Phone> getBooks() {
        List<Phone> phones = new LinkedList<>();
        for (Phone p : libraryManager.findAllPhone()) {
            phones.add(p.clone());
        }
        return phones;
    }

    @RequestMapping(value = "/phones",method = RequestMethod.POST)
    public Phone addBook(@RequestBody Phone nphone) {
        nphone.setId(libraryManager.addPhonejkk(nphone));
        return nphone;
    }

    @RequestMapping(value = "/phone",method = RequestMethod.PUT)
    public String updateBook(@RequestBody Phone nphone) {
        libraryManager.updatePhone(nphone);
        return "ok";
    }




    @RequestMapping("/")
    public String index() {
        return "Hello";
    }

    @RequestMapping(value = "/phone/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Phone getBook(@PathVariable("id") Long id) throws SQLException {
        return libraryManager.findPhoneById(id).clone();
    }

    @RequestMapping(value = "/phones", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Phone> getBooks(@RequestParam(value = "filter", required = false) String f) throws SQLException {
        List<Phone> phones = new LinkedList<Phone>();
        for (Phone p : libraryManager.findAllPhone()) {
            if (f == null) {
                phones.add(p.clone());
            } else if (p.getModel().contains(f)) {
                phones.add(p);
            }
        }
        return phones;
    }

    @RequestMapping(value = "/phone/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteBook(@PathVariable("id") Long id) throws SQLException {
        libraryManager.deletePhone(libraryManager.findPhoneById(id));
        return "OK";
    }

}