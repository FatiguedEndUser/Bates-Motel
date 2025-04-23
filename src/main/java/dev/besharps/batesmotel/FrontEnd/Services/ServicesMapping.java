package dev.besharps.batesmotel.FrontEnd.Services;



import dev.besharps.batesmotel.DB.Services.Services;
import dev.besharps.batesmotel.DB.Services.ServicesRepository;
import dev.besharps.batesmotel.DB.Services.ServicesService;
import dev.besharps.batesmotel.Exceptions.ServiceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/services")
public class ServicesMapping {
    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private ServicesService servicesService;

    // Path where images will be stored
    private final String UPLOAD_DIR = "src/main/resources/static/uploads/services/";

    // Frontend view mappings
    @GetMapping
    public String viewAllServices(Model model) {
        List<Services> allServices = servicesService.getAllServices();
        model.addAttribute("services", allServices);
        model.addAttribute("searching", false);
        return "Services";
    }

    @GetMapping("/category/{category}")
    public String viewServicesByCategory(@PathVariable String category, Model model) {
        List<Services> services = servicesService.getServicesByCategory(category);
        model.addAttribute("services", services);
        model.addAttribute("serviceCategory", category);
        model.addAttribute("searching", false);
        return "Services";
    }

    @GetMapping("/search")
    public String searchServices(
            @RequestParam(required = false) String serviceCategory,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String keyword,
            Model model) {
        List<Services> services = servicesService.searchServices(serviceCategory, maxPrice, keyword);
        model.addAttribute("services", services);
        model.addAttribute("serviceCategory", serviceCategory);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("keyword", keyword);
        model.addAttribute("searching", true);
        return "ServicesSearch";
    }

    // Admin page for managing services
    @GetMapping("/admin/manage")
    public String manageServices(Model model) {
        List<Services> allServices = servicesService.getAllServices();
        model.addAttribute("services", allServices);
        model.addAttribute("newService", new Services());
        return "ServiceManagement";
    }

    @PostMapping("/admin/add")
    public String addService(
            @ModelAttribute("newService") Services service,
            @RequestParam("imageFile") MultipartFile imageFile) {

        if (!imageFile.isEmpty()) {
            try {
                // Create directory if it doesn't exist
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Generate unique filename
                String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path filePath = uploadPath.resolve(uniqueFileName);

                // Save file
                Files.copy(imageFile.getInputStream(), filePath);

                // Set image URL in service object
                service.setImageUrl("/uploads/services/" + uniqueFileName);

            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload image");
            }
        }

        servicesService.saveService(service);
        return "redirect:/services/admin/manage";
    }

    @GetMapping("/admin/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Optional<Services> service = servicesRepository.findById(id);
        if (service.isEmpty()) {
            throw new ServiceNotFoundException("Service not found");
        }
        model.addAttribute("service", service.get());
        return "EditService";
    }

    @PostMapping("/admin/update/{id}")
    public String updateService(
            @PathVariable Integer id,
            @ModelAttribute Services service,
            @RequestParam("imageFile") MultipartFile imageFile) {

        // Get existing service to preserve image if no new one is uploaded
        Optional<Services> existingService = servicesRepository.findById(id);
        if (existingService.isEmpty()) {
            throw new ServiceNotFoundException("Service not found");
        }

        String imageUrl = existingService.get().getImageUrl();

        // Process new image if provided
        if (!imageFile.isEmpty()) {
            try {
                // Create directory if it doesn't exist
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Generate unique filename
                String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path filePath = uploadPath.resolve(uniqueFileName);

                // Save file
                Files.copy(imageFile.getInputStream(), filePath);

                // Set new image URL
                imageUrl = "/uploads/services/" + uniqueFileName;

            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload image");
            }
        }

        // Update service with all fields including image URL
        servicesService.updateService(
                service.getServiceId(),
                service.getServiceName(),
                service.getServiceCategory(),
                service.getServiceCharge(),
                service.getDescription(),
                service.isAvailable(),
                imageUrl
        );

        return "redirect:/services/admin/manage";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteService(@PathVariable Integer id) {
        servicesService.deleteService(id);
        return "redirect:/services/admin/manage";
    }

    // REST API endpoints
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/api/all")
    @ResponseBody
    List<Services> findAll() {
        return servicesRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/{id}")
    @ResponseBody
    Optional<Services> findById(@PathVariable Integer id) {
        Optional<Services> service = servicesRepository.findById(id);
        if (service.isEmpty()) {
            throw new ServiceNotFoundException("Service not found");
        }
        return service;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/category/{category}")
    @ResponseBody
    List<Services> findByCategory(@PathVariable String category) {
        List<Services> services = servicesRepository.findByServiceCategory(category);
        if (services.isEmpty()) {
            throw new ServiceNotFoundException("No services found in this category");
        }
        return services;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/create")
    @ResponseBody
    void create(@Valid @RequestBody Services service) {
        servicesRepository.save(service);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/update")
    @ResponseBody
    public Services update(@Valid @RequestBody Services service) {
        if (!servicesRepository.existsById(service.getServiceId())) {
            throw new ServiceNotFoundException("Service not found");
        }
        return servicesRepository.save(service);
    }
}