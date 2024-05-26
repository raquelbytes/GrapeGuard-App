package com.raquelbytes.grapeguardapi.DTO;

import com.raquelbytes.grapeguardapi.Model.Usuario;
import com.raquelbytes.grapeguardapi.Model.Vinedo;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class VinedoDTO {
    private Integer id;
    private Integer usuario;
    private String nombre;
    private String ubicacion;
    private LocalDate fechaPlantacion;
    private BigDecimal hectareas;
    private List<Integer> cosechaIds;
    private List<Integer> notaIds;
    private List<Integer> tareaIds;

    public static VinedoDTO  converter (Vinedo source) {
        VinedoDTO target = new VinedoDTO();
        target.setId(source.getId());
        target.setUsuario(source.getUsuario().getID_usuario());
        target.setNombre(source.getNombre());
        target.setUbicacion(source.getUbicacion());
        target.setFechaPlantacion(source.getFechaPlantacion());
        target.setHectareas(source.getHectareas());
        if (source.getCosechas() != null) {
            target.setCosechaIds(source.getCosechas().stream().map(cosecha -> cosecha.getId()).collect(Collectors.toList()));
        }
        if (source.getNotas() != null) {
            target.setNotaIds(source.getNotas().stream().map(nota -> nota.getId()).collect(Collectors.toList()));
        }
        if (source.getTareas() != null) {
            target.setTareaIds(source.getTareas().stream().map(tarea -> tarea.getId()).collect(Collectors.toList()));
        }





        return target;
    }


}

