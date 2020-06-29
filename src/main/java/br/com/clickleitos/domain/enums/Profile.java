package br.com.clickleitos.domain.enums;

public enum Profile {
    ADMIN(1,"ROLE_ADMIN"),
    USUARIO(2,"ROLE_USUARIO"),
    ADMIN_UNIDADE(3, "ROLE_UNIDADE");

    private int code;
    private String description;

    private Profile(int code, String description){
        this.code = code;
        this.description = description;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Profile toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (Profile x : Profile.values()) {
            if (cod.equals(x.getCode())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
