package com.product.phoneshop.service.impl;

import com.product.phoneshop.exception.ServiceException;
import com.product.phoneshop.model.Brand;
import com.product.phoneshop.repository.BrandRepository;
import com.product.phoneshop.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

//business logic
@Slf4j
@Service
@RequiredArgsConstructor

public class BrandServiceImpl implements BrandService {

    @Autowired
    private final BrandRepository brandRepository;

    @Override
    public Brand save(Brand entity) {
        return brandRepository.save(entity);
    }

    @Override
    public Brand getById(Long id){
      return brandRepository.findById(id)
               .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND , String.format("brand not found for id= %d", id)));
        //ServiceException: have 2 = check, uncheck
//       if (brandOptional.isPresent()) {
//           return brandOptional.get();
//       }else {
//           throw new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Brand not found with id: %d", id));
//       }
    }

    @Override
    public List<Brand> getAllBrand() {
        return brandRepository.findByActiveTrue();
    }

    @Override
    public Brand update(Long id, Brand source){
        Brand target = getById(id);
        //brand.setName(dto.getName());
        //BrandMapper.INSTANCE.update(target,source);
        target.setName(source.getName());
        //BeanUtils.copyProperties(source, target , "id");
        return brandRepository.save(target);
    }

    @Override
    public void delete(Long id) {
        Brand brand = getById(id);
        brand.setActive(false);
        brandRepository.save(brand);
        //brandRepository.delete(brand);
        log.info("Brand with id: %d deleted".formatted(id));
    }
}
