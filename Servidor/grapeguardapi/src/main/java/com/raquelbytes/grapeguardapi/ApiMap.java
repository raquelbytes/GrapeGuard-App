package com.raquelbytes.grapeguardapi;

public class ApiMap {
    public static final String BASE_URL = "http://localhost:8080";
    /**-------------------------USUARIOS--------------------------------*/
    public static final String USUARIO_BASE = "/usuarios";
    public static final String USUARIO_POR_ID = "/usuario/{ID_usuario}";
    public static final String USUARIO_LOGIN = "/login";
    public static final String USUARIO_REGISTER = "/register";
    public static final String USUARIO_MODIFICAR = "/modify/{ID_usuario}";
    public static final String USUARIO_ELIMINAR = "/delete/{ID_usuario}";

    /**-------------------------VINEDOS--------------------------------*/
    public static final String VINEDO_BASE = "/vinedo";
    public static final String VINEDO_ID = "/{id}";
    public static final String VINEDO_USUARIO_ID = "/usuario/{usuarioId}";
    public static final String VINEDO_USUARIO_NUEVO = "/usuario/{usuarioId}/nuevo";
    public static final String VINEDO_MODIFICAR = "/modify/{id}";
    public static final String VINEDO_ELIMINAR = "/delete/{id}";
    public static final String VINEDO_SUMA_HECTAREAS = "/usuario/{usuarioId}/hectareas/suma";

    /**-------------------------TAREA--------------------------------*/
    public static final String TAREA_BASE = "/tarea";
    public static final String TAREA_POR_VINEDO_ID = "/vinedo/{idVinedo}";
    public static final String TAREA_PENDIENTES_POR_VINEDO_ID = "/vinedo/{idVinedo}/pendientes";
    public static final String TAREA_TERMINADAS_POR_VINEDO_ID = "/vinedo/{idVinedo}/terminadas";
    public static final String TAREA_AGREGAR_POR_VINEDO_ID = "/vinedo/{idVinedo}";
    public static final String TAREA_ELIMINAR_POR_ID = "/delete/{id}";

    /**-------------------------COSECHA--------------------------------*/
    public static final String COSECHA_BASE = "/cosecha";
    public static final String COSECHA_POR_VINEDO_ID = "/vinedo/{idVinedo}";
    public static final String COSECHA_AGREGAR_POR_VINEDO_ID = "/vinedo/{idVinedo}/nuevo";
    public static final String COSECHA_ELIMINAR_POR_ID = "/delete/{id}";
}
