package com.akhmadfaizin.cateringbusofanowner;

public class PesananUbahApproval {
    private boolean isSelected;
    private String approval;
    private String alasan;

    public PesananUbahApproval(boolean isSelected, String approval, String alasan) {
        this.isSelected = isSelected;
        this.approval = approval;
        this.alasan = alasan;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getAlasan() {
        return alasan;
    }

    public void setAlasan(String alasan) {
        this.alasan = alasan;
    }
}
