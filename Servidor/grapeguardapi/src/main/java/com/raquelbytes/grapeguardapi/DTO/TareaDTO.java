package com.raquelbytes.grapeguardapi.DTO;


import com.raquelbytes.grapeguardapi.Model.Tarea;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TareaDTO {
    private Integer id;
    private Integer vinedoId;
    private String tarea;
    private Tarea.EstadoTarea estado;
    private LocalDateTime recordatorio;
    private LocalDate fechaRealizacion;

    public static TareaDTO convert(Tarea source) {
        TareaDTO target = new TareaDTO();
        target.setId(source.getId());
        target.setVinedoId(source.getVinedo().getId());
        target.setTarea(source.getTarea());
        target.setEstado(source.getEstado());
        target.setRecordatorio(source.getRecordatorio());
        target.setFechaRealizacion(source.getFechaRealizacion());
        return target;
    }

    public static Tarea fromEntity(TareaDTO source) {
        Tarea target = new Tarea();
       // target.setVinedo(source.getVinedoId());
        target.setId(source.getId());
        target.setTarea(source.getTarea());
        target.setEstado(source.getEstado());
        target.setRecordatorio(source.getRecordatorio());
        target.setFechaRealizacion(source.getFechaRealizacion());
        return target;
    }

}

