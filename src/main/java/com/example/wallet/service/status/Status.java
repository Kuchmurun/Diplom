package com.example.wallet.service.status;

public enum Status {
    AVAILABLE {
        @Override
        public boolean canOperate() {
            return true;
        }
    },
    BLOCKED,
    DEACTIVATED,
    UNCONFIRMED;

    public boolean canOperate() {
        return false;
    }
}
