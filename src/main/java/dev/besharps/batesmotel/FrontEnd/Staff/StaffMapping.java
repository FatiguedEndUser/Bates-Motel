package dev.besharps.batesmotel.FrontEnd.Staff;

import dev.besharps.batesmotel.DB.Staff.Staff;
import dev.besharps.batesmotel.DB.Staff.StaffRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;


import java.sql.Date;
import java.text.ParseException;

@Controller
@RequestMapping("/staff")
public class StaffMapping {
    private final StaffRepository staffRepository;

    @Autowired
    public StaffMapping(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Register a custom editor for Date fields
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text == null || text.trim().isEmpty()) {
                    setValue(null); // Set null for empty strings
                } else {
                    try {
                        java.util.Date parsedDate = dateFormat.parse(text);
                        setValue(new Date(parsedDate.getTime()));
                    } catch (ParseException e) {
                        throw new IllegalArgumentException("Could not parse date: " + text, e);
                    }
                }
            }

            @Override
            public String getAsText() {
                Date value = (Date) getValue();
                return (value != null ? dateFormat.format(value) : "");
            }
        });
    }
    // Frontend view mappings
    @GetMapping("/all")
    public String allStaff(Model model) {
        List<Staff> allStaff = staffRepository.findAll();
        model.addAttribute("staffList", allStaff);
        return "Staff";
    }

    @GetMapping("/search")
    public String searchStaff(
            @RequestParam(required = false) String jobTitle,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            Model model) {

        List<Staff> staffList;

        // Very basic search implementation
        if (jobTitle != null && !jobTitle.isEmpty()) {
            staffList = staffRepository.findByJobTitle(jobTitle);
        } else if (firstName != null && !firstName.isEmpty()) {
            staffList = staffRepository.findByFirstNameContainingIgnoreCase(firstName);
        } else if (lastName != null && !lastName.isEmpty()) {
            staffList = staffRepository.findByLastNameContainingIgnoreCase(lastName);
        } else {
            staffList = staffRepository.findAll();
        }

        model.addAttribute("staffList", staffList);
        model.addAttribute("jobTitle", jobTitle);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        return "Staff";
    }

    // Admin page for managing staff
    @GetMapping("/admin/manage")
    public String manageStaff(Model model) {
        List<Staff> allStaff = staffRepository.findAll();
        model.addAttribute("staffList", allStaff);
        model.addAttribute("newStaff", new Staff());
        return "StaffManagement";
    }

    @PostMapping("/admin/add")
    public String addStaff(@ModelAttribute Staff staff) {
        staffRepository.save(staff);
        return "redirect:/staff/admin/manage";
    }

    @GetMapping("/admin/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Optional<Staff> staff = staffRepository.findById(id);
        if (staff.isEmpty()) {
            return "redirect:/staff/admin/manage";
        }
        model.addAttribute("staff", staff.get());
        return "EditStaff";
    }

    @PostMapping("/admin/update/{id}")
    public String updateStaff(@PathVariable String id, @ModelAttribute @Valid Staff staff, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return "staff/edit"; // Return to the edit form with errors displayed
        }

        if (staffRepository.existsById(id)) {
            staff.setId(id); // Ensure ID is set correctly
            staffRepository.save(staff);
        }
        return "redirect:/staff/admin/manage";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteStaff(@PathVariable String id) {
      try {
          staffRepository.deleteById(id);
      }catch (Exception m)
      {

      }
      return "redirect:/staff/admin/manage";
    }
}