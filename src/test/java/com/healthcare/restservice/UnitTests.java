package com.healthcare.restservice;


import com.healthcare.restservice.models.Category;
import com.healthcare.restservice.models.Product;
import com.healthcare.restservice.models.ProductCompany;
import com.healthcare.restservice.models.Purchase;
import com.healthcare.restservice.services.CategoryService;
import com.healthcare.restservice.services.ProductService;
import com.healthcare.restservice.services.PurchaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UnitTests {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public ProductService productService;

    public ObjectMapper objectMapper() {
       return new ObjectMapper();
    }

    @MockBean
    public PurchaseService purchaseService;

    @Test
    public void getSingleProduct() throws Exception {
        Mockito.when(this.productService.getProductById(1L)).thenReturn(java.util.Optional.of(new Product(1L)));
        mockMvc.perform(get("/api/customer/product/1")).andExpect(status().isOk());
        Mockito.when(this.productService.getAllProducts())
                .thenReturn(List.of(new Product(1L), new Product(2L)));
        mockMvc.perform(get("/api/customer/all-products")).andExpect(status().isOk());
    }

    @Test
    public void ProductFilterTest() throws Exception {
        mockMvc.perform(post("/api/customer/filter"))
                .andExpect(status().isBadRequest());
        Mockito.when(productService.filterProductsByCategory(1L)).thenReturn(List.of(new Product(1L)));
        mockMvc.perform(post("/api/customer/filter?categoryId=1"))
                .andExpect(status().isAccepted());
        Mockito.when(productService.filterProductsByCompany(new ProductCompany(1L)))
                .thenReturn(List.of(new Product(1L)));
        mockMvc.perform(post("/api/customer/filter?companyId=1"))
                .andExpect(status().isAccepted());

    }

    @Test
    public void purchaseTest() throws Exception {
        Mockito.when(purchaseService.addPurchaseRecord(List.of(new Purchase(1L)), 100.0))
                .thenReturn(true);
        Mockito.when(purchaseService.addPurchaseRecord(List.of(new Purchase(2L)), 0.0))
                .thenReturn(false);
        System.out.println(purchaseService.addPurchaseRecord(List.of(new Purchase(1L)), 100.0));
        mockMvc.perform(post("/api/customer/purchase?total-cost=0.0")
            .contentType("application/json").content(objectMapper()
                        .writeValueAsString(List.of(new Purchase(2L)))))
                .andExpect(status().isPreconditionFailed());
    }
    // Admin Tests

    @MockBean
    public CategoryService categoryService;

    @Test
    public void addNewCategory() throws Exception {
        Mockito.when(categoryService.addNewCategory(Mockito.any(Category.class))).thenReturn(null);
        mockMvc.perform(post("/api/admin/add-new-category")
                .contentType("application/json").content(objectMapper()
                        .writeValueAsString(new Category(1L))))
                .andExpect(status().isBadRequest());
        Category category = new Category(1L);
        Mockito.when(categoryService.addNewCategory(Mockito.any(Category.class))).thenReturn(category);
        System.out.println(categoryService.addNewCategory(category) == null);
        mockMvc.perform(post("/api/admin/add-new-category")
                .contentType("application/json").content(objectMapper()
                        .writeValueAsString(category)))
                .andExpect(status().isOk());
    }

    @Test()
    public void globalExceptionsTest() throws Exception {
        Mockito.when(categoryService.addNewCategory(Mockito.any(Category.class))).thenThrow(new PropertyValueException("", "", ""));
        mockMvc.perform(post("/api/admin/add-new-category")
                .contentType("application/json").content(objectMapper()
                        .writeValueAsString(new Category(1L))))
                        .andExpect(status().isExpectationFailed());
    }
    @Test()
    public void globalExceptionsTest2() throws Exception {
        Mockito.when(categoryService.addNewCategory(Mockito.any(Category.class))).thenThrow(new ConstraintViolationException(null, null, null));
        mockMvc.perform(post("/api/admin/add-new-category")
                .contentType("application/json").content(objectMapper()
                        .writeValueAsString(new Category(1L))))
                .andExpect(status().isConflict());
    }
}
