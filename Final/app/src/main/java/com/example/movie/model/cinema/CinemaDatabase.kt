package com.example.movie.model.cinema

class CinemaDatabase {
    fun  getCinemaList(): List<Cinema> {
        val cinema = Cinema(
            1,
            "Bekmambetov Cinema",
            "пл. Республики 13, Алматыпл. Республики 13, Алматы",
            43.23795,
            76.945586
        )
        val cinema1 = Cinema(
            2,
            "CINEMAX Dostyk Multiplex",
            "мкр.Самал 2, 111 д. в ТРЦ \"Dostyk Plaza\"",
            43.232974,
            76.955679
        )
        val cinema2 = Cinema(
            3,
            "Lumiera Cinema",
            "проспект Абылай Хана 62, Алматы",
            43.262106,
            76.941394
        )
        val cinema3 = Cinema(
            4,
            "Kinopark 11 IMAX Esentai",
            "проспект Аль-Фараби, 77/8 Esentai Mall, Алматы",
            43.218559,
            76.927724
        )
        val cinema4 = Cinema(
            5,
            "Cinema Towers 3D",
            "улица Байзакова 280, Алматы",
            43.237484,
            76.91504
        )
        val cinema5 = Cinema(
            6,
            "Chaplin Cinemas(Mega)",
            "город, ул. Макатаева 127/9, Алматы",
            43.264043,
            76.929472
        )
        val cinema6 = Cinema(
            7,
            "Арман 3D",
            "ТРЦ \"MART\"",
            43.336739,
            76.952996
        )
        val cinema7 = Cinema(
            8,
            "Nomad Cinema",
            "город, проспект Рыскулова 103, Алматы",
            43.267059,
            76.87022
        )
        val cinema8 = Cinema(
            9,
            "Kinopark 6 Sputnik",
            "Мамыр, микрорайон 1, 8а ТРЦ \"Sputnik Mall, Алматы",
            43.211989,
            76.842285
        )
        val cinema9 = Cinema(
            10,
            "Kinopark 8 Moskva",
            "8 микрорайон, 37/1 ТРЦ MOSKVA Metropolitan, Алматы",
            43.226885,
            76.864132
        )
        val cinema10 = Cinema(
            11,
            "Kinopark 5 Atakent",
            "ул. Тимирязева, 42 к3 ТРК \"Atakent Mall, Алматы",
            43.225622,
            76.907748
        )
        val cinema11 = Cinema(
            12,
            "Kinopark 16 Forum",
            "пр. Сейфуллина, 615 ТРЦ \"Forum",
            43.234228,
            76.935831
        )
        val list: MutableList<Cinema> = ArrayList()
        list.add(cinema)
        list.add(cinema1)
        list.add(cinema2)
        list.add(cinema3)
        list.add(cinema5)
        list.add(cinema6)
        list.add(cinema7)
        list.add(cinema8)
        list.add(cinema9)
        list.add(cinema10)
        list.add(cinema11)
        list.add(cinema4)
        return list
    }
}