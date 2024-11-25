package vn.hoidanit.jobhunter.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.dto.Meta;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CompanyService {


    CompanyRepository companyRepository;

    public Company handleCreateCompany(Company company) {
        return this.companyRepository.save(company);
    }


    public ResultPaginationDTO handleGetCompany(Pageable pageable) {
        Page<Company> companyPage = this.companyRepository.findAll(pageable);
        ResultPaginationDTO rs=new ResultPaginationDTO();
        Meta mt=new Meta();

        mt.setPage(companyPage.getNumber()+1);
        mt.setPageSize(companyPage.getSize());
        mt.setTotal(companyPage.getTotalElements());
        mt.setPages(companyPage.getTotalPages());

        rs.setMeta(mt);
        return rs;
    }

    public void handleDeleteCompany(long id) {
        this.companyRepository.deleteById(id);
    }

    public Company handleUpdateCompany(Company c) {
        Optional<Company> companyOptional = this.companyRepository.findById(c.getId());
        if (companyOptional.isPresent()) {
            Company currentCompany = companyOptional.get();
            currentCompany.setLogo(c.getLogo());
            currentCompany.setName(c.getName());
            currentCompany.setDescription(c.getDescription());
            currentCompany.setAddress(c.getAddress());
            return this.companyRepository.save(currentCompany);
        }
        return null;
    }
}