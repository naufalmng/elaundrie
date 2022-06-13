package com.xnfl16.elaundrie.utils

import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import com.itextpdf.text.pdf.draw.VerticalPositionMark
import com.xnfl16.elaundrie.core.data.source.model.Pelanggan
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import kotlin.jvm.Throws

class PdfFactory() {
    private var fileName = ""
    private lateinit var ctx: Context
    fun createPdfFile(ctx: Context, data: Pelanggan?,path: String){
        this.fileName = path
        this.ctx = ctx
        Log.d("PdfFactory",fileName)
        if(File(path).exists())
            File(path).delete()

        try {

            val document = Document()
            PdfWriter.getInstance(document,FileOutputStream(path))
            document.open()

            document.pageSize = PageSize.POSTCARD
            document.addCreationDate()
            document.addAuthor("Admin 1")
            document.addCreator("E-Laundrie")

            val valueFontSize = 30.0f

            val fontName = BaseFont.createFont("res/font/poppins_regular.ttf","UTF-8", BaseFont.EMBEDDED)

            val headingStyle = Font(fontName,36.0f,Font.BOLD,BaseColor.BLACK)
            val titleStyle = Font(fontName,30.0f, Font.NORMAL,BaseColor.BLACK)
            val titleStyleBold = Font(fontName,30.0f, Font.BOLD,BaseColor.BLACK)
            val valueStyle = Font(fontName,valueFontSize,Font.NORMAL,BaseColor.BLACK)


            addNewItemWithLeftAndRight(document,"E-Laundrie","",Font(fontName,40.0f,Font.BOLD,BaseColor.BLACK),headingStyle)
            addLineSpace(document)
            addNewItemWithLeftAndRight(document, data?.tanggalDanWaktu.toString(),"",headingStyle,headingStyle)
            addNewItemWithLeftAndRight(document,"ID: ${data?.id.toString()}","",headingStyle,headingStyle)
            addLineSeperator(document)
            addNewItemWithLeftAndRight(document,"Nama: ",data?.nama.toString(),titleStyle,valueStyle)
            addLineSeperator(document)
            addNewItemWithLeftAndRight(document,"Alamat: ",data?.alamat.toString(),titleStyle,valueStyle)
            addLineSeperator(document)
            addNewItemWithLeftAndRight(document,"Jumlah/Kg: ",data?.jumlah.toString()+" Kg",titleStyle,valueStyle)
            addLineSeperator(document)
            addNewItemWithLeftAndRight(document,"Diskon %: ",data?.diskon.toString()+" %",titleStyle,valueStyle)
            addLineSeperator(document)
            addNewItemWithLeftAndRight(document,"Layanan: ",data?.layanan.toString(),titleStyle,valueStyle)
            addLineSeperator(document)
            addNewItemWithLeftAndRight(document,"Status Pembayaran: ",data?.status.toString(),titleStyleBold,titleStyleBold)
            addLineSeperator(document)
            addNewItemWithLeftAndRight(document,"Total: ",data?.total.toString(),titleStyleBold,titleStyleBold)
            addLineSeperator(document)
            document.close()
            printPdf()

        }catch(e: Exception){
            Log.e("PdfFactory: ",e.message.toString())
        }
    }

    private fun printPdf() {
        val printManager: PrintManager = ctx.getSystemService(Context.PRINT_SERVICE) as PrintManager
        try {
            val printAdapter = PdfDocumentAdapter(ctx,fileName)
            printManager.print("Document",printAdapter,PrintAttributes.Builder().build())
        }catch (e: Exception){
            Log.e("PdfFactory: ","${e.message}")
        }
    }

    @Throws(DocumentException::class)
    private fun addNewItemWithLeftAndRight(
        document: Document,
        leftText: String,
        rightText: String,
        leftStyle: Font,
        rightStyle: Font
    ) {
        val chunkTextLeft = Chunk(leftText,leftStyle)
        val chunkTextRight = Chunk(rightText,rightStyle)
        val p = Paragraph(chunkTextLeft)
        p.add(Chunk(VerticalPositionMark()))
        p.add(chunkTextRight)
        document.add(p)
    }

    @Throws(DocumentException::class)
    private fun addLineSeperator(document: Document) {
        val lineSeperator = LineSeparator()
        lineSeperator.lineColor = BaseColor(0,0,0,68)
        addLineSpace(document)
        document.add(Chunk(lineSeperator))
        addLineSpace(document)
    }

    @Throws(DocumentException::class)
    private fun addLineSpace(document: Document) {
        document.add(Paragraph(""))
    }
}