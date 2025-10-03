package $PKG

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_moldes).setOnClickListener {
            // TODO: abrir lista de moldes
        }
        findViewById<Button>(R.id.btn_mantenimiento).setOnClickListener {
            // TODO: abrir pantalla de mantenimientos
        }
        findViewById<Button>(R.id.btn_inventario).setOnClickListener {
            // TODO: abrir inventario
        }
    }
}
