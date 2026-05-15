package com.openingtablexml.data

object OpeningRepository {
    fun getOpenings(): List<ChessOpening> {
        return listOf(
            ChessOpening(
                id = "1",
                name = "Italian Game",
                ecoCode = "C50",
                moves = "1. e4 e5 2. Nf3 Nc6 3. Bc4",
                category = "Open Game",
                description = "Pembukaan tertua dalam catur, berfokus pada penguasaan pusat dengan pion dan perkembangan cepat perwira sayap raja. Gajah di c4 mengincar titik lemah f7 milik Hitam.",
                imageUrl = "https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/PedroPinhata/phpdX1kmE.png",
                externalUrl = "https://www.chess.com/openings/Italian-Game",
                isFeatured = true
            ),
            ChessOpening(
                id = "2",
                name = "Queen's Gambit",
                ecoCode = "D06",
                moves = "1. d4 d5 2. c4",
                category = "Closed Game",
                description = "Pembukaan agresif di mana Putih 'mengorbankan' pion sayap menteri untuk mendapatkan kendali pusat yang kuat. Sangat populer dan sering dimainkan di tingkat master.",
                imageUrl = "https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/PedroPinhata/phpFpDkU1.png",
                externalUrl = "https://www.chess.com/openings/Queens-Gambit",
                isFeatured = true
            ),
            ChessOpening(
                id = "3",
                name = "Sicilian Defense",
                ecoCode = "B20",
                moves = "1. e4 c5",
                category = "Semi-Open",
                description = "Jawaban paling populer dan mematikan terhadap 1.e4. Hitam langsung menantang pusat secara asimetris, mengarah ke posisi taktis yang sangat tajam dan kompleks.",
                imageUrl = "https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/PedroPinhata/phpbqYjSK.png",
                externalUrl = "https://www.chess.com/openings/Sicilian-Defense",
                isFeatured = false
            ),
            ChessOpening(
                id = "4",
                name = "Caro-Kann Defense",
                ecoCode = "B10",
                moves = "1. e4 c6",
                category = "Semi-Open",
                description = "Mirip dengan French Defense tetapi Gajah terang Hitam tidak terblokir. Terkenal sebagai pertahanan yang sangat solid dan sulit ditembus.",
                imageUrl = "https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/hscer/phpeBurY9.png",
                externalUrl = "https://www.chess.com/openings/Caro-Kann-Defense",
                isFeatured = false
            ),
            ChessOpening(
                id = "5",
                name = "Scandinavian Defense",
                ecoCode = "B01",
                moves = "1. e4 d5",
                category = "Semi-Open",
                description = "Hitam langsung menantang e4 di langkah pertama. Sering kali memaksa Putih memakan pion dan Hitam membawa Menterinya keluar lebih awal.",
                imageUrl = "https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/hscer/phpyr6VJi.png",
                externalUrl = "https://www.chess.com/openings/Scandinavian-Defense",
                isFeatured = false
            )
        )
    }
}