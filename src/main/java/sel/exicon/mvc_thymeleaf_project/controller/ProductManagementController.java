package sel.exicon.mvc_thymeleaf_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sel.exicon.mvc_thymeleaf_project.dto.ProductDetailsDto;
import sel.exicon.mvc_thymeleaf_project.dto.ProductDto;
import sel.exicon.mvc_thymeleaf_project.service.ProductService;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/product")
public class ProductManagementController {

    ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    // http request type = GET
    // URL = http://localhost:8080/admin/product/
    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("productDtoList", productService.getAll());
        return "productManagement";
    }


    @GetMapping("/find/{id}")
    public String getProductById(@PathVariable("id") Integer id, Model model) {
        System.out.println("ID: " + id);
        ProductDto optionalProductDto = productService.findById(id);
        model.addAttribute("productDto", optionalProductDto);
        return "adminProductDetails";
    }

    @GetMapping("/addForm")
    public String registerForm(Model model) {
        ProductDto dto = new ProductDto();
        model.addAttribute("dto", dto);
        return "productAddForm";
    }


    @PostMapping("/add")
    public String add(@ModelAttribute("dto") @Valid ProductDto productDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (productDto.getName().startsWith("J")){
            FieldError error= new FieldError("dto","name","Name should not be started with J character :) ");
            bindingResult.addError(error);
        }

        if (bindingResult.hasErrors()){
            return "productAddForm";
        }


        productService.saveOrUpdate(productDto);
        redirectAttributes.addFlashAttribute("message", "Add Product Name: " + productDto.getName() + " is Done");
        redirectAttributes.addFlashAttribute("alertClass","alert-success");

        return "redirect:/admin/product/";
    }


    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        productService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Delete Product ID: " +id + " is Done");
        redirectAttributes.addFlashAttribute("alertClass","alert-info");
        return "redirect:/admin/product/";
    }


    @GetMapping("/uploadImageForm/{id}")
    public String showImageForm(@PathVariable("id") Integer id, Model model){
        //productService.findById(id);
        model.addAttribute("id",id);
        return "productAddImageFrom";
    }


    @PostMapping("/upload/{id}")
    public String uploadFile(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file,RedirectAttributes redirectAttributes){
        System.out.println("id = " + id);
        System.out.println("file.getSize() = "+file.getSize());
        System.out.println("Image Size KB:" + file.getSize() / 1024);
        if (file.getSize() == 0){
            redirectAttributes.addFlashAttribute("message","Select Image file (jpg/png)");
            redirectAttributes.addFlashAttribute("alertClass","alert-danger");
            return "redirect:/admin/product/uploadImageForm/"+id;
        }

        if (file.getSize()/1024 >= 300){
            redirectAttributes.addFlashAttribute("message","Image size is not valid ( max size should be less than 300 KB )");
            redirectAttributes.addFlashAttribute("alertClass","alert-danger");
            return "redirect:/admin/product/uploadImageForm/"+id;
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (!(fileName.endsWith(".jpg") || fileName.endsWith(".png")) ){
            redirectAttributes.addFlashAttribute("message","Image type is not valid");
            redirectAttributes.addFlashAttribute("alertClass","alert-danger");
            return "redirect:/admin/product/uploadImageForm/"+id;
        }
        ProductDto dto= new ProductDto();
        Path path= Paths.get("src/main/resources/static/images/"+fileName);
        try {
            Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
            dto = productService.findById(id);
            dto.getProductDetailsDto().setImage(file.getBytes());
            productService.saveOrUpdate(dto);
            redirectAttributes.addFlashAttribute("message","Image successfully uploaded. file name : " + fileName);
            redirectAttributes.addFlashAttribute("alertClass","alert-success");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/admin/product/";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {

        ProductDto dto = productService.findById(id);

        model.addAttribute("dto", dto);
        return "productEditForm";
    }


    @PostMapping("/edit")
    public String edit(@ModelAttribute("dto") @Valid ProductDto productDto,RedirectAttributes redirectAttributes) {
        System.out.println("productDto.toString() = " + productDto.toString());

        ProductDto findByIdDto = productService.findById(productDto.getId());

        productDto.getProductDetailsDto().setImage(findByIdDto.getProductDetailsDto().getImage());
        productService.saveOrUpdate(productDto);

        redirectAttributes.addFlashAttribute("message","Update Operation is completed");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/admin/product/";
    }


}
