/**
 * Item.js
 *
 * @description :: TODO: You might write a short summary of how this model works and what it represents here.
 * @docs        :: http://sailsjs.org/documentation/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {
    "id": {type:'objectid',primaryKey:true},
    "nama_item":{type:"string"},
    "pilihan" :[{
      "nama": {type:"string"},
      "deskripsi": {type:"string"},
      "harga": {type:"number"},
      "url_img": {type:"string"}
    }]
  }
};

