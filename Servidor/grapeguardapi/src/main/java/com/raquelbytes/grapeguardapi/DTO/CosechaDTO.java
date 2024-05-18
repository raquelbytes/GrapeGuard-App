package com.raquelbytes.grapeguardapi.DTO;
import com.raquelbytes.grapeguardapi.Model.Cosecha;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

    @Data
    public class CosechaDTO {
        private Integer id;
        private Integer vinedoId;
        private String nombreVariedad;
        private BigDecimal cantidadUvas;
        private BigDecimal precioVentaKg;
        private LocalDate fechaCosecha;

        public static CosechaDTO convert(Cosecha source) {
            CosechaDTO target = new CosechaDTO();
            target.setId(source.getId());
            target.setVinedoId(source.getVinedo().getId());
            target.setNombreVariedad(source.getNombreVariedad());
            target.setCantidadUvas(source.getCantidadUvas());
            target.setPrecioVentaKg(source.getPrecioVentaKg());
            target.setFechaCosecha(source.getFechaCosecha());
            return target;
        }

        public static Cosecha fromEntity(CosechaDTO source) {
            Cosecha target = new Cosecha();
            target.setId(source.getId());
            // Assuming you set Vinedo elsewhere or you have a method to get it
            // target.setVinedo(...);
            target.setNombreVariedad(source.getNombreVariedad());
            target.setCantidadUvas(source.getCantidadUvas());
            target.setPrecioVentaKg(source.getPrecioVentaKg());
            target.setFechaCosecha(source.getFechaCosecha());
            return target;
        }
    }


