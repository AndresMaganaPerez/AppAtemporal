package com.example.appatemporal.framework.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.appatemporal.R
import com.example.appatemporal.databinding.DetailedMetricsBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.DetailedMetricsViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailedMetrics : AppCompatActivity(){

    private lateinit var binding : DetailedMetricsBinding
    private lateinit var ourDashTitle : TextView
    private lateinit var ourIngresosTotales : TextView
    private lateinit var ourPMBarChart: BarChart
    private lateinit var ourTTSABarChart: BarChart

    private val detailedMetricsViewModel : DetailedMetricsViewModel by viewModels()

    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailed_metrics)

        binding = DetailedMetricsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Implementación de la NavBar
        binding.navbar.homeIcon.setOnClickListener {
            finish()
        }

        binding.navbar.budgetIcon.setOnClickListener {
            val intent = Intent(this, ProyectoOrganizador::class.java)
            startActivity(intent)
        }

        binding.navbar.ticketsIcon.setOnClickListener {
            finish()
        }

        binding.navbar.metricsIcon.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

        ourDashTitle = findViewById(R.id.dashTitle)
        ourIngresosTotales = findViewById(R.id.profitsEvent)
        ourPMBarChart = findViewById(R.id.PMLinechart)
        ourTTSABarChart = findViewById(R.id.TTASLinechart)

        repository = Repository(this)

        val tempEventId : String = "DM"

        setEventName(tempEventId)
        setTotalProfit(tempEventId)

        var dataTbyPM : MutableList<Pair<String,Int?>> = mutableListOf()
        detailedMetricsViewModel.getPMbyTickets(tempEventId,repository)
        detailedMetricsViewModel.countPM.observe(this, Observer{
            for(element in it){
                dataTbyPM.add(Pair(element.key,element.value))
            }
            setBCPMbyEvent(dataTbyPM)
        })

        var dataTTSA : MutableList<Triple<String,Int?,Int?>> = mutableListOf()
        detailedMetricsViewModel.getTypeSA(tempEventId,repository)
        detailedMetricsViewModel.eventsTicketsTypeSA.observe(this, Observer{
            for(element in it){
                dataTTSA.add(Triple(element.key,element.value.first,element.value.second))
            }
            setTTSABarChart(dataTTSA)
        })
    }

    private fun setEventName(eid:String) {
        repository = Repository(this)
        detailedMetricsViewModel.getEventName(eid,repository)
        detailedMetricsViewModel.eventName.observe(this, Observer{
            ourDashTitle.text = "${it}"
        })
    }

    private fun setTotalProfit(eid:String) {
        repository = Repository(this)
        detailedMetricsViewModel.getEventProfit(eid,repository)
        detailedMetricsViewModel.eventProfit.observe(this, Observer{
            val profit: String = NumberFormat.getNumberInstance(Locale.US).format(it)
            ourIngresosTotales.text = "$"+profit+" MXN"
        })
    }

    private fun setBCPMbyEvent(dataList : MutableList<Pair<String,Int?>>){
        val ourPMBarChart = binding.PMLinechart
        //declare values of the chart
        //dataset - boletos por metodo de pago
        val dataSet: ArrayList<BarEntry> = ArrayList()
        var i = 0
        for (entry in dataList) {
            var value = dataList[i].second!!.toFloat()
            dataSet.add(BarEntry(i.toFloat(), value))
            i++
        }
        //bardata set
        val bardataSet = BarDataSet(dataSet,"Metodos de pago")
        bardataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        val data = BarData(bardataSet)
        //pass the data to the BarChar
        ourPMBarChart.data = data
        //declare the XAxis variable
        val xAxis: XAxis = ourPMBarChart.xAxis
        //set the labels on the chart
        val xAxisLabels: ArrayList<String> = ArrayList()
        var k = 0
        for (entry in dataList) {
            xAxisLabels.add(dataList[k].first)
            k++
        }
        ourPMBarChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)
        // Formato de los datos
        bardataSet.valueTextSize = 8f
        bardataSet.valueFormatter = DefaultValueFormatter(0)
        //decorative elements of the chart
        xAxis.setCenterAxisLabels(false)

        xAxis.position = XAxis.XAxisPosition.TOP

        xAxis.setGranularity(1f)
        xAxis.setGranularityEnabled(true)

        //ourPMBarChart.axisLeft.axisMaximum = bardataSet.xMax
        ourPMBarChart.axisLeft.axisMinimum = 0f
        ourPMBarChart.axisRight.axisMinimum = 0f

        ourPMBarChart.setDragEnabled(false)
        ourPMBarChart.setScaleEnabled(false)
        //ourPMBarChart.setVisibleXRangeMaximum(3f)
        //decorative elements of the chart
        ourPMBarChart.axisLeft.setDrawGridLines(false)
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        ourPMBarChart.legend.isEnabled = false
        ourPMBarChart.description.isEnabled = false
        ourPMBarChart.animateY(1000)
        ourPMBarChart.invalidate()
    }

    private fun setTTSABarChart(dataList : MutableList<Triple<String,Int?,Int?>>){
        //declare values of the chart
        //dataset 1 - ventas totales
        val entriesVTotales: ArrayList<BarEntry> = ArrayList()
        var i = 0
        for (entry in dataList) {
            var value = dataList[i].second!!.toFloat()
            Log.d("Dentro de la grafica 1",value.toString())
            entriesVTotales.add(BarEntry(i.toFloat(), value))
            i++
        }
        //dataset 2 - asistencias totales
        val entriesVAsistencias: ArrayList<BarEntry> = ArrayList()
        var j = 0
        for (entry in dataList) {
            var value = dataList[j].third!!.toFloat()
            Log.d("Dentro de la grafica 2",value.toString())
            entriesVAsistencias.add(BarEntry(j.toFloat(), value))
            j++
        }
        Log.d("Dataset2",entriesVAsistencias.toString())
        //bardata set
        val bardataSet1 = BarDataSet(entriesVTotales,"Ventas totales")
        bardataSet1.setColors(resources.getColor(R.color.Red))
        val bardataSet2 = BarDataSet(entriesVAsistencias,"Asistencias totales")
        bardataSet2.setColors(resources.getColor(R.color.purple_200))
        val data = BarData(bardataSet1,bardataSet2)
        //pass the data to the BarChar
        ourTTSABarChart.data = data
        //declare the XAxis variable
        val xAxis: XAxis = ourTTSABarChart.xAxis
        //set the labels on the chart
        val xAxisLabels: ArrayList<String> = ArrayList()
        var k = 0
        for (entry in dataList) {
            xAxisLabels.add(dataList[k].first)
            k++
        }
        ourTTSABarChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)

        xAxis.setCenterAxisLabels(true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setGranularity(1f)
        //xAxis.setGranularityEnabled(false)

        ourTTSABarChart.setDragEnabled(false)
        ourTTSABarChart.setScaleEnabled(false)
        ourTTSABarChart.setVisibleXRangeMaximum(3f)
        val barSpace = 0.1f
        val groupSpace = 0.01f
        data.barWidth = 0.15f

        ourTTSABarChart.xAxis.axisMinimum = 0f
        ourTTSABarChart.groupBars(0f, groupSpace, barSpace)

        //decorative elements of the chart
        ourTTSABarChart.axisLeft.setDrawGridLines(false)
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        ourTTSABarChart.legend.isEnabled = false
        ourTTSABarChart.description.isEnabled = false
        ourTTSABarChart.animateY(1000)
        //xAxis.setGranularityEnabled(true)
        //ourTTSABarChart.setVisibleXRange(1f,1f)
        ourTTSABarChart.invalidate()
    }

}