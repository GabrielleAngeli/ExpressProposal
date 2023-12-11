package br.edu.ifsp.scl.expressproposal

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.R
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.os.Bundle
import android.os.Environment
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.scl.expressproposal.data.company.Company
import br.edu.ifsp.scl.expressproposal.databinding.ActivityProposalBinding
import br.edu.ifsp.scl.expressproposal.viewmodel.company.CompanyViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ProposalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProposalBinding
    private lateinit var viewModel: CompanyViewModel

    var pageHeight = 1120
    var pagewidth = 792

    var bmp: Bitmap? = null
    var scaledbmp: Bitmap? = null

    private val PERMISSION_REQUEST_CODE = 200
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProposalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scaledbmp = Bitmap.createScaledBitmap(scaledbmp!!, 50, 50, false)

        binding.buttonGenerateProposal.setOnClickListener {
            var companySelected: Company? = null
                viewModel.allCompanies.observe(this) { companies ->
                    companies?.let {
                        companySelected =
                            companies.firstOrNull { it.name == binding.spinnerCompany.selectedItem.toString() }
                    }
            }

            val title = binding.editTextProposalCode.text.toString()
            if (checkPermission()) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                requestPermission();
            }
            generatePDF(title, companySelected )
        }

        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this).get(CompanyViewModel::class.java)

        viewModel.allCompanies.observe(this) { companies ->

            companies?.let {
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, companies.map { it.name })
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerCompany.adapter = adapter
            }
        }

        binding.spinnerCompany.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                val selectedCompany = parent?.getItemAtPosition(position).toString()
                binding.textViewCompany.text = "Empresa Selecionada: $selectedCompany"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun generatePDF(titleProposal: String, selectedCompany: Company?) {
        val pdfDocument = PdfDocument()
        val paint = Paint()
        val title = Paint()
        val mypageInfo = PageInfo.Builder(pagewidth, pageHeight, 1).create()

        val myPage = pdfDocument.startPage(mypageInfo)

        val canvas = myPage.canvas


        canvas.drawBitmap(scaledbmp!!, 56f, 40f, paint)


        title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)


        title.textSize = 15f

        title.color = ContextCompat.getColor(this, R.color.black)

        canvas.drawText(titleProposal, 209f, 100f, title)
        selectedCompany?.let {
            canvas.drawText(it.name, 209f, 160f, title)
            canvas.drawText(it.cnpj, 209f, 180f, title)
            canvas.drawText(it.email, 209f, 200f, title)
            it.phone?.let { phone -> canvas.drawText(phone, 209f, 220f, title) }

        }

        canvas.drawText("Proposta Comercial", 209f, 80f, title)

        title.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        title.color = ContextCompat.getColor(this, R.color.black)
        title.textSize = 15f


        title.textAlign = Paint.Align.CENTER
        canvas.drawText("Aqui deverá aparecer futuramente uma tabela com os itens que farão parte da prop0sta comercial", 396f, 560f, title)


        pdfDocument.finishPage(myPage)

        val fileName = "${ selectedCompany?.name}.pdf"

        val file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)


        try {

            pdfDocument.writeTo(FileOutputStream(file))


            Toast.makeText(
                this@ProposalActivity,
                "PDF file generated successfully.",
                Toast.LENGTH_SHORT
            ).show()

            val uri = FileProvider.getUriForFile(this, "br.edu.ifsp.scl.expressproposal.fileprovider", file)

            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "application/pdf")
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            startActivity(intent)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        pdfDocument.close()
    }

    private fun checkPermission(): Boolean {
        // checking of permissions.
        val permission1 =
            ContextCompat.checkSelfPermission(applicationContext, WRITE_EXTERNAL_STORAGE)
        val permission2 =
            ContextCompat.checkSelfPermission(applicationContext, READ_EXTERNAL_STORAGE)
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(
            this,
            arrayOf<String>(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.size > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                val writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}