package br.edu.ifsp.scl.expressproposal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.navigation.ui.AppBarConfiguration
import br.edu.ifsp.scl.expressproposal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Configurar a Toolbar como a barra de ação
        setSupportActionBar(binding.toolbar)

        val btnNavigateToList: Button = findViewById(R.id.btnNavigateToList)
        btnNavigateToList.setOnClickListener {
            // Navegar para a tela de listagem
            val intent = Intent(this, ListCompaniesActivity::class.java)
            startActivity(intent)
        }

        val btnNavigateToListItems: Button = findViewById(R.id.btnNavigateToListItems)
        btnNavigateToListItems.setOnClickListener {
            // Navegar para a tela de listagem
            val intent = Intent(this, ListItemsActivity::class.java)
            startActivity(intent)
        }
    }

}