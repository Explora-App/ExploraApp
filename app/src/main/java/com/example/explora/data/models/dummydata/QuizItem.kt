package com.example.explora.data.models.dummydata

data class QuizItem(val number: Int,val pertanyaan: String,val pilihan_benar: String)

val myDataset = listOf(
    QuizItem(1, "Apa jenis batang yang dimiliki oleh tanaman pepaya?", "Batang bertipe basah"),
    QuizItem(2, "Ciri khas daun pada tanaman lidah buaya?", "Daun tebal, Bertekstur, Warna hijau"),
    QuizItem(3, "Berapa lama waktu yang dibutuhkan untuk pepaya berbuah?", "6-9 bulan setelah penanaman"),
    QuizItem(4, "Apa manfaat ekstrak lidah buaya dalam produk perawatan kulit?", "Melembapkan, Mengurangi kemerahan, Mempercepat regenerasi sel kulit"),
    QuizItem(5, "Apa nama ilmiah dari tanaman pepaya?", "Carica papaya"),
    QuizItem(6, "Bagaimana cara merawat tanaman pepaya agar produktif?", "Memberikan pupuk organik, Menjaga kelembaban tanah, Pemangkasan yang tepat"),
    QuizItem(7, "Apa nama senyawa aktif yang terdapat dalam lidah buaya?", "Aloin")
)
