package com.example.tugaspertemuan5

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun FormBiodata(modifier: Modifier = Modifier) {
    // --- STATE FORM BIODATA ---
    var namaDepan by remember { mutableStateOf("") }
    var namaBelakang by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var tglLahir by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }

    var isAndroidChecked by remember { mutableStateOf(false) }
    var isUiUxChecked by remember { mutableStateOf(false) }
    var isWebChecked by remember { mutableStateOf(false) }

    // --- NATIVE ANDROID DATE PICKER DIALOG ---
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                tglLahir = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    // --- DROPDOWN STATE ---
    var expandedExperience by remember { mutableStateOf(false) }
    val experienceOptions = listOf(
        stringResource(id = R.string.exp_fresh),
        stringResource(id = R.string.exp_1_2),
        stringResource(id = R.string.exp_3_5),
        stringResource(id = R.string.exp_more_5)
    )

// --- LOGIKA WARNA BUTTON BERDASARKAN RESOURCE COLOR ---
    val isSkillSelected = isAndroidChecked || isUiUxChecked || isWebChecked
    val btnBgColor = if (isSkillSelected) {
        colorResource(id = R.color.btn_proceed_active_bg)
    } else {
        colorResource(id = R.color.btn_proceed_inactive_bg)
    }
    val btnTextColor = if (isSkillSelected) {
        colorResource(id = R.color.btn_proceed_active_text)
    } else {
        colorResource(id = R.color.btn_proceed_inactive_text)
    }

    // --- SNACKBAR STATE & COROUTINE ---
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val emptyDash = stringResource(id = R.string.empty_value)
    val androidSkill = stringResource(id = R.string.skill_android)
    val uiuxSkill = stringResource(id = R.string.skill_uiux)
    val webSkill = stringResource(id = R.string.skill_web)
    val previewFormat = stringResource(id = R.string.preview_biodata)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 1. Gambar Latar Belakang
            Image(
                painter = painterResource(id = R.drawable.legrab),
                contentDescription = stringResource(id = R.string.desc_bg_header),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentScale = ContentScale.Crop
            )

            // 2. Card/Surface Form Mengapung
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 220.dp),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = colorResource(id = R.color.bg_card_biodata)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // --- Judul & Subjudul ---
                    Text(
                        text = stringResource(id = R.string.title_form_biodata),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.purple_title)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = stringResource(id = R.string.subtitle_form_biodata),
                        fontSize = 13.sp,
                        color = colorResource(id = R.color.text_subtitle_gray)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // --- Baris 1: Nama Depan & Nama Belakang ---
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = namaDepan,
                            onValueChange = { namaDepan = it },
                            label = { Text(text = stringResource(id = R.string.label_nama_depan)) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        OutlinedTextField(
                            value = namaBelakang,
                            onValueChange = { namaBelakang = it },
                            label = { Text(text = stringResource(id = R.string.label_nama_belakang)) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // --- Baris 2: Email ---
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(text = stringResource(id = R.string.label_email)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    // --- Baris 3: Tgl Lahir & Experience ---
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Tgl Lahir
                        Box(modifier = Modifier.weight(1f)) {
                            OutlinedTextField(
                                value = tglLahir,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text(text = stringResource(id = R.string.label_tgl_lahir)) },
                                trailingIcon = {
                                    IconButton(onClick = { datePickerDialog.show() }) {
                                        Icon(
                                            imageVector = Icons.Default.DateRange,
                                            contentDescription = stringResource(id = R.string.desc_icon_calendar)
                                        )
                                    }
                                },
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.fillMaxWidth()
                            )
                            // Overlay agar seluruh area kotak bisa diklik
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .clickable { datePickerDialog.show() }
                            )
                        }
                        // Experience Dropdown
                        Box(modifier = Modifier.weight(1f)) {
                            OutlinedTextField(
                                value = experience,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text(text = stringResource(id = R.string.label_experience)) },
                                trailingIcon = {
                                    IconButton(onClick = { expandedExperience = true }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = stringResource(id = R.string.desc_icon_dropdown)
                                        )
                                    }
                                },
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.fillMaxWidth()
                            )
                            // Overlay agar seluruh area kotak bisa diklik
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .clickable { expandedExperience = true }
                            )

                            DropdownMenu(
                                expanded = expandedExperience,
                                onDismissRequest = { expandedExperience = false }
                            ) {
                                experienceOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(text = option) },
                                        onClick = {
                                            experience = option
                                            expandedExperience = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // --- Section Skill ---
                    Text(
                        text = stringResource(id = R.string.title_pilih_skill),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.black),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))



