package vn.hoidanit.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.service.CompanyService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;


@RestController
@RequestMapping(value = "/companies")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyController {

    private CompanyService companyService;

    @PostMapping("")
    public ResponseEntity<?> createCompany(@Valid @RequestBody Company reqCompany) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.companyService.handleCreateCompany(reqCompany));
    }


    @GetMapping("")
    @ApiMessage("fetch all company")
    public ResponseEntity<?> getAllCompany(
            @Filter Specification<Company> specification,
            Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.handleGetAllCompany(specification, pageable));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable("id") long id) {
        this.companyService.handleDeleteCompany(id);
        return ResponseEntity.ok("đã xoóa thành công");
    }

    @PutMapping("")
    public ResponseEntity<?> updateCompany(@Valid @RequestBody Company reqCompany) {
        Company updatedCompany = this.companyService.handleUpdateCompany(reqCompany);
        if (updatedCompany == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy công ty với ID: " + reqCompany.getId());
        }
        return ResponseEntity.ok(updatedCompany);
    }


}
