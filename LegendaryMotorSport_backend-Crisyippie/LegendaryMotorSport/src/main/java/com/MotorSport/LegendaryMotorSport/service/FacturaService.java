package com.MotorSport.LegendaryMotorSport.service;

import com.MotorSport.LegendaryMotorSport.model.factura.DetalleFactura;
import com.MotorSport.LegendaryMotorSport.model.factura.DetalleFacturaDTO;
import com.MotorSport.LegendaryMotorSport.model.factura.Factura;
import com.MotorSport.LegendaryMotorSport.model.factura.FacturaDTO;
import com.MotorSport.LegendaryMotorSport.model.orderModel.Order;
import com.MotorSport.LegendaryMotorSport.repository.FacturaRepository;
import com.MotorSport.LegendaryMotorSport.repository.VehicleRepository;
import org.springframework.stereotype.Service;



import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacturaService {

    private final FacturaRepository facturaRepository;
   

    public FacturaService(FacturaRepository facturaRepository, VehicleRepository vehicleRepository) {
        this.facturaRepository = facturaRepository;
    
    }

    public FacturaDTO generarFacturaDesdeOrden(Order order, String metodoPago, String tipoDocumento) {
        Factura factura = new Factura();
        factura.setOrder(order);
        factura.setUserId(order.getUserId());
        factura.setFechaEmision(LocalDateTime.now());
        factura.setMetodoPago(metodoPago);
        factura.setTipoDocumento(tipoDocumento);

        List<DetalleFactura> detalles = order.getItems().stream().map(item -> {
            DetalleFactura detalle = new DetalleFactura();
            detalle.setFactura(factura);
            detalle.setVehicle(item.getVehicle());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getPrecioUnitario());
            detalle.setPrecioTotal(item.getPrecioTotal());
            return detalle;
        }).collect(Collectors.toList());

        factura.setDetalle(detalles);
        factura.setMontoTotal(detalles.stream().mapToDouble(DetalleFactura::getPrecioTotal).sum());

        Factura saved = facturaRepository.save(factura);
        return mapToDTO(saved); 
    }


    public FacturaDTO mapToDTO(Factura factura) {
        FacturaDTO dto = new FacturaDTO();
        dto.setId(factura.getId());
        dto.setUserId(factura.getUserId());
        dto.setFechaEmision(factura.getFechaEmision());
        dto.setMontoTotal(factura.getMontoTotal());
        dto.setMetodoPago(factura.getMetodoPago());
        dto.setTipoDocumento(factura.getTipoDocumento());

        List<DetalleFacturaDTO> detalles = factura.getDetalle().stream().map(detalle -> {
            DetalleFacturaDTO d = new DetalleFacturaDTO();
            d.setVehicleId(detalle.getVehicle().getId());
            d.setModelo(detalle.getVehicle().getModel());
            d.setCantidad(detalle.getCantidad());
            d.setPrecioUnitario(detalle.getPrecioUnitario());
            d.setPrecioTotal(detalle.getPrecioTotal());
            return d;
        }).collect(Collectors.toList());

        dto.setDetalle(detalles);
        return dto;
    }


    public List<FacturaDTO> obtenerTodasLasFacturas() {
        return facturaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    public List<FacturaDTO> obtenerFacturasPorUsuario(Long userId) {
    return facturaRepository.findByUserId(userId).stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    public Optional<FacturaDTO> obtenerFacturaPorOrderId(Long orderId) {
    return facturaRepository.findByOrderId(orderId)
            .map(this::mapToDTO);
    }

    public void eliminarFactura(Long id) {
        facturaRepository.deleteById(id);
    }
}
