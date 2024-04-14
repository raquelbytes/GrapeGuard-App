package com.raquelbytes.grapeguardapi.Controller;

import com.raquelbytes.grapeguardapi.Model.Producto;
import com.raquelbytes.grapeguardapi.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // Métodos para la entidad Producto

    @GetMapping("/productos")
    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }

    @GetMapping("/productos/{id}")
    public Producto obtenerProductoPorId(@PathVariable(value = "id") Integer productoId) {
        return productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoId));
    }

    @PostMapping("/productos")
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    @PutMapping("/productos/{id}")
    public Producto actualizarProducto(@PathVariable(value = "id") Integer productoId,
                                       @RequestBody Producto productoDetails) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoId));

        producto.setNombre(productoDetails.getNombre());
        producto.setCantidad(productoDetails.getCantidad());
        producto.setPrecioUnitario(productoDetails.getPrecioUnitario());

        return productoRepository.save(producto);
    }

    @DeleteMapping("/productos/{id}")
    public void eliminarProducto(@PathVariable(value = "id") Integer productoId) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoId));

        productoRepository.delete(producto);
    }
}
