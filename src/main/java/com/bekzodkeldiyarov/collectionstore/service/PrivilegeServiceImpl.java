package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.Privilege;
import com.bekzodkeldiyarov.collectionstore.repository.PrivilegeRepository;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    private final PrivilegeRepository privilegeRepository;

    public PrivilegeServiceImpl(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public Privilege findByName(String name) {
        return privilegeRepository.findByName(name);
    }

    @Override
    public Privilege save(Privilege privilege) {
        return privilegeRepository.save(privilege);
    }
}
