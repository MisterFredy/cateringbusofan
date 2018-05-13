/**
 * Pemesanan.js
 *
 * @description :: TODO: You might write a short summary of how this model works and what it represents here.
 * @docs        :: http://sailsjs.org/documentation/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {
    "pemesan":{type:"string"},
    "pengaprove":{type:"string"},
    "tanggalPemesanan":{type:"date"},
    "Approval":{type:"string"},
    "totalBayar":{type:"number"},
    "totalDP":{type:"number"},
      "detaiPemesanan":[{
        "kategori":{type:"string"},
        "menu":{type:"string"},
        "kutantiti":{type:"number"},
            "detailMenu":[{
              type:"string"
            }],
        "tanggalPengiriman":{type:"date"},
        "atasNama":{type:"string"},
        "kontak":{type:"number"},
        "alamat":{type:"string"},
        "catatan":{type:"string"},
        "status":{type:"number"}
      }],
  }
};

