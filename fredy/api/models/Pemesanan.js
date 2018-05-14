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
    "detaiPemesanan":{type:"json"}
  }
};

