CREATE DATABASE  IF NOT EXISTS `bibliotek_test` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bibliotek_test`;
CREATE TABLE bibliotek_test.bog LIKE bibliotek.bog;
CREATE TABLE bibliotek_test.forfatter LIKE bibliotek.forfatter;
CREATE TABLE bibliotek_test.laaner LIKE bibliotek.laaner;
CREATE TABLE bibliotek_test.postnummer LIKE bibliotek.postnummer;
CREATE TABLE bibliotek_test.udlaan LIKE bibliotek.udlaan;
CREATE TABLE bibliotek_test.bruger LIKE bibliotek.bruger;