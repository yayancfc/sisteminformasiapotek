/*
SQLyog Ultimate v12.4.1 (32 bit)
MySQL - 10.1.13-MariaDB : Database - 10115416_apotekdb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`10115416_apotekdb` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `10115416_apotekdb`;

/*Table structure for table `t_detail_penjualan` */

DROP TABLE IF EXISTS `t_detail_penjualan`;

CREATE TABLE `t_detail_penjualan` (
  `Id_Penjualan` varchar(8) DEFAULT NULL,
  `Kode_Obat` varchar(8) DEFAULT NULL,
  `Harga_Satuan` double DEFAULT NULL,
  `Jumlah` int(11) DEFAULT NULL,
  `Total_Harga` double DEFAULT NULL,
  KEY `Id_Penjualan` (`Id_Penjualan`),
  KEY `Kode_Obat` (`Kode_Obat`),
  CONSTRAINT `t_detail_penjualan_ibfk_1` FOREIGN KEY (`Id_Penjualan`) REFERENCES `t_transaksi_penjualan` (`Id_Penjualan`) ON UPDATE CASCADE,
  CONSTRAINT `t_detail_penjualan_ibfk_2` FOREIGN KEY (`Kode_Obat`) REFERENCES `t_obat` (`Kode_Obat`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_detail_penjualan` */

insert  into `t_detail_penjualan`(`Id_Penjualan`,`Kode_Obat`,`Harga_Satuan`,`Jumlah`,`Total_Harga`) values 
('1','ELAN',14482,2,28964),
('1','SUMA-T',375,2,750);

/*Table structure for table `t_karyawan` */

DROP TABLE IF EXISTS `t_karyawan`;

CREATE TABLE `t_karyawan` (
  `Id_Karyawan` varchar(8) NOT NULL,
  `Nama_Karyawan` varchar(30) DEFAULT NULL,
  `Jenis_Kelamin` varchar(15) DEFAULT NULL,
  `Jabatan` varchar(30) DEFAULT NULL,
  `Status` varchar(30) DEFAULT NULL,
  `Alamat` varchar(30) DEFAULT NULL,
  `Kota` varchar(15) DEFAULT NULL,
  `No_Telepon` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`Id_Karyawan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_karyawan` */

insert  into `t_karyawan`(`Id_Karyawan`,`Nama_Karyawan`,`Jenis_Kelamin`,`Jabatan`,`Status`,`Alamat`,`Kota`,`No_Telepon`) values 
('K01','Indra Agung Iskandar','Laki-laki','Admin','Belum Menikah','Jl. Terusan Pasir Koja No.20','Bandung','085223881711'),
('K02','Farida Erlin Darmali','Perempuan','Pelayan','Menikah','Jl. Karapitan No.111, Cikawao','Bandung','083817206835'),
('KD03','Muhammad Ridwan','Laki-laki','Kasir','Menikah','Jl. Cisokan No.20, Sukamaju','Bandung','089646454558');

/*Table structure for table `t_obat` */

DROP TABLE IF EXISTS `t_obat`;

CREATE TABLE `t_obat` (
  `Kode_Obat` varchar(8) NOT NULL,
  `Nama_Obat` varchar(30) DEFAULT NULL,
  `Satuan` varchar(20) DEFAULT NULL,
  `Kategori` varchar(20) DEFAULT NULL,
  `Expired` date DEFAULT NULL,
  `Stok` int(11) DEFAULT NULL,
  `Harga_Beli` double DEFAULT NULL,
  `Harga_Jual` double DEFAULT NULL,
  `Keuntungan` double DEFAULT NULL,
  PRIMARY KEY (`Kode_Obat`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_obat` */

insert  into `t_obat`(`Kode_Obat`,`Nama_Obat`,`Satuan`,`Kategori`,`Expired`,`Stok`,`Harga_Beli`,`Harga_Jual`,`Keuntungan`) values 
('ACIT','ACITRAL 120ML','Sirup','Obat Dalam','2021-11-01',0,23440,25784,2344),
('ACPUL','ACPULSIF 5','Tablet','Obat Dalam','2019-07-02',0,3025,3328,303),
('ACR-150','ACRAN150','Tablet','Obat Dalam','2018-06-20',0,4521,4973,452),
('ELAN','ENALOS','Strip','Obat Dalam','2017-11-15',265,12700,14482,1782),
('EPIM-10','EPIMAS 10S','Kapsul','Obat Dalam','2020-07-06',0,20490,23000,2510),
('EPIM-30','EPIMAS 30S','Kapsul','Obat Dalam','2017-07-05',0,45900,50000,4100),
('EVER-T','EVERJOY','Tablet','Obat Dalam','2018-10-18',0,28600,31500,2900),
('FIRD-O','FIRDAUS OIL','Botol	','Obat Luar','2020-09-13',0,23000,27000,4000),
('GALF-F','GALFLUX','Tablet','Obat Dalam','2017-07-05',0,3520,3870,350),
('LYNO-R','LYNORAL','Tablet','Obat Dalam','2017-11-30',0,1471,1780,309),
('MYK','MT KF 100','Botol	','Obat Dalam','2017-05-19',0,11900,12700,800),
('PER-B','PERBAN KH 5CM','Lain-lain','Obat Luar','2017-07-13',0,1545,1700,155),
('PERV','PERVITA','Tablet','Obat Dalam','2017-07-05',0,1160,1280,120),
('PROME','PROMEDEX 60ML','Sirup','Obat Dalam','2020-07-10',0,15355,17000,1645),
('RENV-25','RENVOL-25','Tablet','Obat Dalam','2017-03-01',0,690,860,170),
('RENV-50','RENVOL 50','Tablet','Obat Dalam','2019-03-01',0,1320,1452,132),
('RM-D 7.5','ST REMEDY 7.5','Lain-lain','Obat Luar','2017-07-05',0,4950,5445,495),
('SPYR','SPYROCON','Tablet','Obat Dalam','2018-02-15',0,19250,21175,1925),
('SUMA-T','SUMAGESIC TAB','Tablet','Obat Dalam','2017-07-14',98,312,375,63),
('VOR G','VOREN GEL 20 G','Salep','Obat Luar','2017-12-27',0,20086,25108,5022),
('VOSE-S','VOSEDON SYRUP','Sirup','Obat Dalam','2017-07-06',0,26180,28798,2618),
('VRO-500','VROXIL 500 MG','Tablet','Obat Dalam','2017-07-04',0,8800,9680,880),
('ZEN-600','ZENA 600','Lain-lain','Obat Dalam','2017-09-01',0,30765,36000,5235),
('ZINC','ZINCPRO SYR','Sirup','Obat Dalam','2019-01-01',200,23100,25410,2310),
('ZOT 5 G','ZOTER 5 G','Salep','Obat Luar','2017-10-22',0,37207,40928,3721),
('ZTR-200','ZOTER 200','Tablet','Obat Dalam','2017-11-27',50,5125,5638,513);

/*Table structure for table `t_supplier` */

DROP TABLE IF EXISTS `t_supplier`;

CREATE TABLE `t_supplier` (
  `Id_Supplier` varchar(8) NOT NULL,
  `Nama_Supplier` varchar(30) DEFAULT NULL,
  `Alamat` varchar(100) DEFAULT NULL,
  `Kota` varchar(15) DEFAULT NULL,
  `No_Telepon` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`Id_Supplier`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_supplier` */

insert  into `t_supplier`(`Id_Supplier`,`Nama_Supplier`,`Alamat`,`Kota`,`No_Telepon`) values 
('SU01','PT Prima Solusi Medika','Komp. Ruko Mutiara Taman Palem Blok A19 No. 2-3','Jakarta','(021) 92003336'),
('SU02','PT Nu Skin Indonesia','Jl. Jendral Sudirman kav 42-43','Jakarta','(021) 30030088'),
('SU03','DragonNoni','Jl. Taman Pemuda no. 2','Cirebon','( 0231 ) 341236'),
('SU04','PT Metro Pillars Group','Jl Cempaka Km 37 Bl B/6 Kompl Niaga Kalimas','Bekasi','( 021) 88357528'),
('SU05','PT Dexa Medica','Jl Kelapa Gading','Tangerang','(021) 54200134');

/*Table structure for table `t_transaksi_pembelian` */

DROP TABLE IF EXISTS `t_transaksi_pembelian`;

CREATE TABLE `t_transaksi_pembelian` (
  `Id_Pembelian` varchar(8) NOT NULL,
  `Tanggal` date DEFAULT NULL,
  `Id_Supplier` varchar(8) DEFAULT NULL,
  `Kode_Obat` varchar(8) DEFAULT NULL,
  `Harga` double DEFAULT NULL,
  `Jumlah` int(11) DEFAULT NULL,
  `Total` double DEFAULT NULL,
  PRIMARY KEY (`Id_Pembelian`),
  KEY `Id_Supplier` (`Id_Supplier`),
  KEY `Kode_Obat` (`Kode_Obat`),
  CONSTRAINT `t_transaksi_pembelian_ibfk_1` FOREIGN KEY (`Id_Supplier`) REFERENCES `t_supplier` (`Id_Supplier`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_transaksi_pembelian_ibfk_2` FOREIGN KEY (`Kode_Obat`) REFERENCES `t_obat` (`Kode_Obat`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_transaksi_pembelian` */

insert  into `t_transaksi_pembelian`(`Id_Pembelian`,`Tanggal`,`Id_Supplier`,`Kode_Obat`,`Harga`,`Jumlah`,`Total`) values 
('BL1','2017-07-23','SU01','SUMA-T',312,100,31200),
('BL2','2017-07-06','SU01','ELAN',12700,50,635000),
('BL7','2017-07-03','SU04','ZTR-200',5125,50,256250),
('BL8','2017-07-19','SU03','ZINC',23100,200,4620000),
('BL9','2017-07-12','SU02','ELAN',12700,217,2755900);

/*Table structure for table `t_transaksi_penjualan` */

DROP TABLE IF EXISTS `t_transaksi_penjualan`;

CREATE TABLE `t_transaksi_penjualan` (
  `Id_Penjualan` varchar(8) NOT NULL,
  `Tanggal` date DEFAULT NULL,
  `Id_Karyawan` varchar(8) DEFAULT NULL,
  `Total_Harga` double DEFAULT NULL,
  PRIMARY KEY (`Id_Penjualan`),
  KEY `t_transaksi_penjualan_ibfk_1` (`Id_Karyawan`),
  CONSTRAINT `t_transaksi_penjualan_ibfk_1` FOREIGN KEY (`Id_Karyawan`) REFERENCES `t_karyawan` (`Id_Karyawan`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_transaksi_penjualan` */

insert  into `t_transaksi_penjualan`(`Id_Penjualan`,`Tanggal`,`Id_Karyawan`,`Total_Harga`) values 
('1','2017-07-23','K02',29714);

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `Id_User` varchar(8) NOT NULL,
  `Username` varchar(30) DEFAULT NULL,
  `Password` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`Id_User`),
  KEY `Id_Karyawan` (`Id_User`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_user` */

insert  into `t_user`(`Id_User`,`Username`,`Password`) values 
('A01','admin','admin'),
('A02','yayan','yayan'),
('A03','iqbal','iqbal'),
('A04','septa','septa'),
('A05','nicko','nicko');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
