package com.crud.wallet.dao;

import com.crud.wallet.model.Wallet;
import com.crud.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletDao {
    @Autowired
    WalletRepository walletRepository;


    public Wallet save(Wallet walet) {
        return walletRepository.save(walet);
    }


    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }

    public List<Wallet> deleteAll() {
        walletRepository.deleteAll();
        return walletRepository.findAll();
    }


    public Wallet findOne(Long id) {
        return walletRepository.findOne(id);
    }


    public void delete(Wallet prod) {
        walletRepository.delete(prod);
    }
}
