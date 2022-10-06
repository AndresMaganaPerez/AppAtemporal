package com.example.appatemporal.framework.view

import com.example.appatemporal.domain.models.boletoPorEventoClass

class boletosPorEventoProvider {
    companion object {
        val proyectoList = listOf<boletoPorEventoClass>(
            boletoPorEventoClass("Fetish", "Lun 03 Oct", "19:00 hrs"),
            boletoPorEventoClass("Kodak Black", "Vie 27 Oct", "17:00 hrs"),
            boletoPorEventoClass("Bungus", "Mie 14 Nov", "20:00 hrs"),
            boletoPorEventoClass("Degiheugi", "Sáb 08 Oct", "21:00 hrs"),
            boletoPorEventoClass("Zack Bryan", "Mar 09 Oct", "20:00 hrs"),
            boletoPorEventoClass("Moby", "Jue 11 Oct", "21:00 hrs"),
        )
    }
}
