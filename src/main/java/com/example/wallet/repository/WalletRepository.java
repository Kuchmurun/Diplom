package com.example.wallet.repository;

import com.example.wallet.entity.WalletEntity;
import com.example.wallet.service.FillWallet;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class WalletRepository implements DAO {
    public static final RowMapper<WalletEntity> walletRowMapper = new RowMapper<WalletEntity>() {
        @Override
        public WalletEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            WalletEntity walletEntity = new WalletEntity();
            walletEntity.setId(rs.getInt("id"));
            walletEntity.setFirst_name(rs.getString("first_name"));
            walletEntity.setMiddle_name(rs.getString("middle_name"));
            walletEntity.setLast_name(rs.getString("last_name"));
            walletEntity.setPassport_id(rs.getInt("passport_id"));
            walletEntity.setRub(rs.getDouble("rub"));
            walletEntity.setUsd(rs.getDouble("usd"));
            walletEntity.setEur(rs.getDouble("eur"));
            walletEntity.setWalletStatus(rs.getString("wallet_status"));
            return walletEntity;
        }
    };
    private final JdbcTemplate template;
    private final FillWallet wallet;

    WalletRepository(JdbcTemplate template, FillWallet wallet) {
        this.template = template;
        this.wallet = wallet;
    }

    @Override
    public List<WalletEntity> readWallet() {
        String sql = "SELECT * FROM wallets;";
        return template.query(sql, walletRowMapper);
    }

    @Override
    public WalletEntity readWalletById(int id) throws EmptyResultDataAccessException {
        String sql = "SELECT * FROM wallets WHERE id = ?";
        return template.queryForObject(sql, walletRowMapper, id);
    }

    @Override
    public List<Integer> getAllId() {
        String sql = "SELECT id FROM wallets;";
        return template.queryForList(sql, Integer.class);
    }

    @Override
    public void createWallet(WalletEntity walletEntity) {
        String sql = "INSERT INTO wallets (first_name, last_name, middle_name, passport_id, rub, usd, eur, wallet_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        template.update(sql, walletEntity.getFirst_name(), walletEntity.getLast_name(), walletEntity.getMiddle_name(), walletEntity.getPassport_id(), walletEntity.getRub(),
                walletEntity.getUsd(), walletEntity.getEur(), walletEntity.getDefaultWalletStatus());
    }

    @Override
    public void updateWallet(WalletEntity walletEntity) {
        String sql = "UPDATE wallets SET first_name = ?, last_name = ?, middle_name = ?, passport_id = ?, rub = ?," +
                " usd = ?, eur = ? WHERE id = ?";
        template.update(sql, walletEntity.getFirst_name(), walletEntity.getLast_name(), walletEntity.getMiddle_name(), walletEntity.getPassport_id(),
                walletEntity.getRub(), walletEntity.getUsd(), walletEntity.getEur(), walletEntity.getId());

    }

    @Override
    public void fillBalance(int id, double amount, String currency) {
        double sum = wallet.fill(readWalletById(id), amount, currency);
        String sql = "UPDATE wallets SET " + currency + " = ? WHERE id = ?";
        template.update(sql, sum, id);
    }

    @Override
    public void deleteWallet(int id) {
        String sql = "DELETE FROM wallets WHERE id = ?";
        template.update(sql, id);
    }
}
