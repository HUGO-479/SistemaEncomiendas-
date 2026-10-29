package org.example.encomienda.dto;

public class PreRegistroDTO {
    private String remitenteDni;
    private String remitenteNombres;
    private String remitenteApellidos;
    private String remitenteCelular;

    private String destinatarioDni;
    private String destinatarioNombres;
    private String destinatarioApellidos;
    private String destinatarioCelular;

    private Long agenciaOrigenId;
    private Long agenciaDestinoId;
    private String descripcionBulto;
    private Long tipoPaqueteId;
    private Double pesoEstimado;

    // --- GETTERS Y SETTERS A MANO ---
    public String getRemitenteDni() { return remitenteDni; }
    public void setRemitenteDni(String remitenteDni) { this.remitenteDni = remitenteDni; }

    public String getRemitenteNombres() { return remitenteNombres; }
    public void setRemitenteNombres(String remitenteNombres) { this.remitenteNombres = remitenteNombres; }

    public String getRemitenteApellidos() { return remitenteApellidos; }
    public void setRemitenteApellidos(String remitenteApellidos) { this.remitenteApellidos = remitenteApellidos; }

    public String getRemitenteCelular() { return remitenteCelular; }
    public void setRemitenteCelular(String remitenteCelular) { this.remitenteCelular = remitenteCelular; }

    public String getDestinatarioDni() { return destinatarioDni; }
    public void setDestinatarioDni(String destinatarioDni) { this.destinatarioDni = destinatarioDni; }

    public String getDestinatarioNombres() { return destinatarioNombres; }
    public void setDestinatarioNombres(String destinatarioNombres) { this.destinatarioNombres = destinatarioNombres; }

    public String getDestinatarioApellidos() { return destinatarioApellidos; }
    public void setDestinatarioApellidos(String destinatarioApellidos) { this.destinatarioApellidos = destinatarioApellidos; }

    public String getDestinatarioCelular() { return destinatarioCelular; }
    public void setDestinatarioCelular(String destinatarioCelular) { this.destinatarioCelular = destinatarioCelular; }

    public Long getAgenciaOrigenId() { return agenciaOrigenId; }
    public void setAgenciaOrigenId(Long agenciaOrigenId) { this.agenciaOrigenId = agenciaOrigenId; }

    public Long getAgenciaDestinoId() { return agenciaDestinoId; }
    public void setAgenciaDestinoId(Long agenciaDestinoId) { this.agenciaDestinoId = agenciaDestinoId; }

    public String getDescripcionBulto() { return descripcionBulto; }
    public void setDescripcionBulto(String descripcionBulto) { this.descripcionBulto = descripcionBulto; }

    public Long getTipoPaqueteId() { return tipoPaqueteId; }
    public void setTipoPaqueteId(Long tipoPaqueteId) { this.tipoPaqueteId = tipoPaqueteId; }

    public Double getPesoEstimado() { return pesoEstimado; }
    public void setPesoEstimado(Double pesoEstimado) { this.pesoEstimado = pesoEstimado; }
}