package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.moldescmms.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        // TALLER DE MOLDES - Botón amarillo abre LISTA de solicitudes recibidas
        findViewById<Button>(R.id.btn_solicitudes).setOnClickListener {
            startActivity(Intent(this, SolicitudesRecibidasActivity::class.java))
        }

        findViewById<Button>(R.id.btn_moldes).setOnClickListener {
            startActivity(Intent(this, MoldesListActivity::class.java))
        }

        findViewById<Button>(R.id.btn_mantenimientos).setOnClickListener {
            startActivity(Intent(this, MantenimientosListActivity::class.java))
        }

        findViewById<Button>(R.id.btn_herramientas).setOnClickListener {
            startActivity(Intent(this, HerramientasListActivity::class.java))
        }

        findViewById<Button>(R.id.btn_refacciones).setOnClickListener {
            startActivity(Intent(this, RefaccionesListActivity::class.java))
        }

        // MANTENIMIENTO DE MÁQUINAS
        findViewById<Button>(R.id.btn_maquinas).setOnClickListener {
            startActivity(Intent(this, MaquinasActivity::class.java))
        }

        findViewById<Button>(R.id.btn_mantenimientos_maquinas).setOnClickListener {
            startActivity(Intent(this, MaquinasActivity::class.java))
        }

        findViewById<Button>(R.id.btn_solicitudes_mantenimiento_maquinas).setOnClickListener {
            startActivity(Intent(this, SolicitudesActivity::class.java))
        }

        // PRODUCCIÓN - Abre el menú de producción con botón de solicitud
        findViewById<Button>(R.id.btn_productos).setOnClickListener {
            startActivity(Intent(this, ProduccionMainActivity::class.java))
        }

        findViewById<Button>(R.id.btn_ordenes_produccion).setOnClickListener {
            startActivity(Intent(this, RegistroProduccionActivity::class.java))
        }

        findViewById<Button>(R.id.btn_operadores).setOnClickListener {
            startActivity(Intent(this, OperadoresActivity::class.java))
        }

        findViewById<Button>(R.id.btn_registro_produccion).setOnClickListener {
            startActivity(Intent(this, RegistroProduccionActivity::class.java))
        }

        findViewById<Button>(R.id.btn_asignacion_produccion).setOnClickListener {
            startActivity(Intent(this, AsignacionProduccionActivity::class.java))
        }

        findViewById<Button>(R.id.btn_preparacion_material).setOnClickListener {
            startActivity(Intent(this, PreparacionMaterialActivity::class.java))
        }

        // ALMACÉN
        findViewById<Button>(R.id.btn_inventario).setOnClickListener {
            startActivity(Intent(this, InventarioActivity::class.java))
        }

        findViewById<Button>(R.id.btn_requerimientos_insumo).setOnClickListener {
            startActivity(Intent(this, RequerimientosInsumoActivity::class.java))
        }

        findViewById<Button>(R.id.btn_refacciones_maquina).setOnClickListener {
            startActivity(Intent(this, RefaccionesMaquinaActivity::class.java))
        }

        // CALIDAD - Abre el menú de calidad con botón de solicitud
        findViewById<Button>(R.id.btn_calidad).setOnClickListener {
            startActivity(Intent(this, CalidadActivity::class.java))
        }

        // COMPRAS
        findViewById<Button>(R.id.btn_proveedores).setOnClickListener {
            startActivity(Intent(this, ProveedoresActivity::class.java))
        }

        findViewById<Button>(R.id.btn_ordenes_compra).setOnClickListener {
            startActivity(Intent(this, OrdenesCompraListActivity::class.java))
        }
    }
}
