package com.example.appatemporal.framework.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.appatemporal.R
import com.example.appatemporal.databinding.DetailedMetricsBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.DetailedMetricsViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.detailed_metrics.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailedMetrics : AppCompatActivity(){

    private lateinit var binding : DetailedMetricsBinding
    private val detailedMetricsViewModel : DetailedMetricsViewModel by viewModels()
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailed_metrics)

        val userUid = getSharedPreferences("user", Context.MODE_PRIVATE)
            .getString("userUid", "").toString()

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

        repository = Repository(this)

        val tempEventId : String = "Nbb94T1aTzqT4RiXfmWm"

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

        var revenuePM : MutableList<Pair<String,Int?>> = mutableListOf()
        detailedMetricsViewModel.getRevenuePM(tempEventId,repository)
        detailedMetricsViewModel.revenueByPM.observe(this, Observer{
            for(element in it){
                revenuePM.add(Pair(element.key,element.value))
            }
            setRevenueByPM(revenuePM)
        })
    }

    private fun setEventName(eid:String) {
        val ourDashTitle = binding.dashTitle
        repository = Repository(this)
        detailedMetricsViewModel.getEventName(eid,repository)
        detailedMetricsViewModel.eventName.observe(this, Observer{
            ourDashTitle.text = "${it}"
        })
    }

    private fun setTotalProfit(eid:String) {
        val ourIngresosTotales = binding.profitsEvent
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
        val ourTTSABarChart = binding.TTASLinechart
        //Declaramos los datos de la grafica

        //Creacion de dataSets
        val bardataSet1 = BarDataSet(getTTSAset1(dataList),"Ventas totales")
        bardataSet1.setColors(resources.getColor(R.color.Red))

        val bardataSet2 = BarDataSet(getTTSAset2(dataList),"Asistencias totales")
        bardataSet2.setColors(resources.getColor(R.color.purple_200))

        val data = BarData(bardataSet1,bardataSet2)
        //pass the data to the BarChar
        ourTTSABarChart.data = data

        bardataSet1.valueTextSize = 8f
        bardataSet2.valueTextSize = 8f

        ourTTSABarChart.description.isEnabled = false

        //declare the XAxis variable
        val xAxis = ourTTSABarChart.xAxis
        //set the labels on the chart

        xAxis.valueFormatter = IndexAxisValueFormatter(getTTSAlabels(dataList))

        xAxis.setCenterAxisLabels(false)

        xAxis.position = XAxis.XAxisPosition.BOTTOM

        xAxis.setGranularity(1f)
        xAxis.setGranularityEnabled(true)

        val barSpace = 0.1f
        val groupSpace = 0.25f
        data.barWidth = 0.15f

        ourTTSABarChart.setDragEnabled(false)
        ourTTSABarChart.setScaleEnabled(false)

        // Formato de los datos
        bardataSet1.valueFormatter = DefaultValueFormatter(0)
        bardataSet2.valueFormatter = DefaultValueFormatter(0)

        //decorative elements of the chart
        ourTTSABarChart.axisLeft.setDrawGridLines(false)
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        ourTTSABarChart.legend.isEnabled = true
        ourTTSABarChart.animateY(1000)

        //Log.d("TTSA labels - a",xAxisLabels.toString())
        ourTTSABarChart.groupBars(-0.24f, groupSpace, barSpace)
        //xAxis.axisMinimum = 0f
        ourTTSABarChart.invalidate()
        //Log.d("TTSA labels - d",xAxisLabels.toString())
    }

    private fun getTTSAset1(dataList : MutableList<Triple<String,Int?,Int?>>): ArrayList<BarEntry>{
        val entriesVT: ArrayList<BarEntry> = ArrayList()
        var i = 0
        for (entry in dataList) {
            var value = dataList[i].second!!.toFloat()
            Log.d("Dentro de la grafica 1",value.toString())
            entriesVT.add(BarEntry(i.toFloat(), value))
            i++
        }
        return entriesVT
    }

    private fun getTTSAset2(dataList : MutableList<Triple<String,Int?,Int?>>): ArrayList<BarEntry>{
        val entriesVA: ArrayList<BarEntry> = ArrayList()
        var j = 0
        for (entry in dataList) {
            var value = dataList[j].third!!.toFloat()
            entriesVA.add(BarEntry(j.toFloat(), value))
            j++
        }
        return entriesVA
    }

    private fun getTTSAlabels(dataList : MutableList<Triple<String,Int?,Int?>>): ArrayList<String>{
        val xAxisLabels: ArrayList<String> = ArrayList()
        var k = 0
        for (entry in dataList) {
            xAxisLabels.add(dataList[k].first)
            k++
        }
        Log.d("Contenido en labels", xAxisLabels.toString())
        return xAxisLabels
    }

    private fun setRevenueByPM(dataList : MutableList<Pair<String,Int?>>){
        val ourRPMHorizontalBarChart = binding.RPMHorizontalBarChart
        //declare values of the chart
        //dataset - ventas por metodo de pago
        val dataSet: ArrayList<BarEntry> = ArrayList()
        var i = 0
        for (entry in dataList) {
            var value = dataList[i].second!!.toFloat()
            dataSet.add(BarEntry(i.toFloat(), value))
            i++
        }
        //bardata set
        val radardataSet = BarDataSet(dataSet,"Metodos de pago")
        radardataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        val data = BarData(radardataSet)
        //pass the data to the BarChar
        ourRPMHorizontalBarChart.data = data
        //set the labels on the chart
        val xAxisLabels: ArrayList<String> = ArrayList()
        var k = 0
        for (entry in dataList) {
            xAxisLabels.add(dataList[k].first)
            k++
        }

        val xAxis = ourRPMHorizontalBarChart.xAxis

        ourRPMHorizontalBarChart.axisLeft.axisMinimum = 0f
        radardataSet.valueTextSize = 8f

        ourRPMHorizontalBarChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)
        //set decorative elements

        xAxis.setGranularity(1f)
        xAxis.setGranularityEnabled(true)

        xAxis.labelRotationAngle = 45f
        xAxis.textSize = 8f
        //ourRPMHorizontalBarChart.getXAxis().setEnabled(false);
        ourRPMHorizontalBarChart.legend.isEnabled = false
        ourRPMHorizontalBarChart.description.isEnabled = false
        ourRPMHorizontalBarChart.animateX(1000)

        ourRPMHorizontalBarChart.invalidate()
    }

}