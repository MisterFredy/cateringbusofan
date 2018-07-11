/**
 * Route Mappings
 * (sails.config.routes)
 *
 * Your routes map URLs to views and controllers.
 *
 * If Sails receives a URL that doesn't match any of the routes below,
 * it will check for matching files (images, scripts, stylesheets, etc.)
 * in your assets directory.  e.g. `http://localhost:1337/images/foo.jpg`
 * might match an image file: `/assets/images/foo.jpg`
 *
 * Finally, if those don't match either, the default 404 handler is triggered.
 * See `api/responses/notFound.js` to adjust your app's 404 logic.
 *
 * Note: Sails doesn't ACTUALLY serve stuff from `assets`-- the default Gruntfile in Sails copies
 * flat files from `assets` to `.tmp/public`.  This allows you to do things like compile LESS or
 * CoffeeScript for the front-end.
 *
 * For more information on configuring custom routes, check out:
 * http://sailsjs.org/#!/documentation/concepts/Routes/RouteTargetSyntax.html
 */

module.exports.routes = {

  /***************************************************************************
  *                                                                          *
  * Make the view located at `views/homepage.ejs` (or `views/homepage.jade`, *
  * etc. depending on your default view engine) your home page.              *
  *                                                                          *
  * (Alternatively, remove this and add an `index.html` file in your         *
  * `assets` directory)                                                      *
  *                                                                          *
  ***************************************************************************/

  '/': {
    view: 'homepage'
  },

  /***************************************************************************
  *                                                                          *
  * Custom routes here...                                                    *
  *                                                                          *
  * If a request to a URL doesn't match any of the custom routes above, it   *
  * is matched against Sails route blueprints. See `config/blueprints.js`    *
  * for configuration options and examples.                                  *
  *                                                                          *
  ***************************************************************************/

  /*CRUD USER API*/
 'get /userapi': {
  controller	: 'userapi',
  action		: 'index'
  },
  'get /userdetail/:username':{
    controller : 'userapi',
    action : 'getuserdetail'
  },
  'post /edituser/:username':{
    controller : 'userapi',
    action : 'updateuserapi'
  },
  'post /tambahuser':{
    controller : 'userapi',
    action : 'tambahuser'
  },
  'post /updtoken/:username/:token':{
    controller : 'userapi',
    action : 'updtoken'
  },
  'post /bayargaji/:username':{},
  'get /rekapabsensi/:username':{},

  'post /uploadfileuser/:username':{
    controller : 'userapi',
    action: 'uploadfiles'
  },

  'post /uploadbukti/:idpemesanan/:nominal/:tanggal':{
    controller : 'Buktibayarpesanan',
    action: 'uploadbukti'
  },

  'post /deletebukti/:id':{
    controller : 'Buktibayarpesanan',
    action: 'deletebukti'
  },

  'post /bukticocok/:id':{
    controller : 'Buktibayarpesanan',
    action: 'bukticocok'
  },

  'post /buktitidakcocok/:id':{
    controller : 'Buktibayarpesanan',
    action: 'buktitidakcocok'
  },


  /* ROUTE TAMBAHAN DARI AKHMAD */
  'post /updatepassword/:username/:password':{
    controller : 'userapi',
    action : 'updatepassword'
  },

  'post /deletepemesanan/:id':{
    controller : 'Pemesanan',
    action : 'deletepemesanan'
  },

  'post /approvalbelum/:id/:pengaprove':{
    controller : 'Pemesanan',
    action : 'approvalbelum'
  },

  'post /approvalsudah/:id/:pengaprove':{
    controller : 'Pemesanan',
    action : 'approvalsudah'
  },

  'post /approvallunas/:id/:pengaprove':{
    controller : 'Pemesanan',
    action : 'approvallunas'
  },

  'get /joinorder':{
    controller : 'Pemesanan',
    action : 'joinpemesanan'
  },

  'post /updatedp/:id/:dp':{
    controller : 'Pemesanan',
    action : 'updatedp'
  },

 /* END OF ROUTE TAMBAHAN DARI AKHMAD */



  /*menu API LIST 
  'get /menuapi':{
    controller: 'menuapi',
    action :'index'
  },

  'post /addmenu':{
    controller: 'menuapi',
    action : 'tambah'
  },

  'get /carikategori/:kategori':{
    controller: 'menuapi',
    action: 'carikategori'
  },

  'get /cariaktifmenu':{
    controller: 'menuapi',
    action: 'cariaktifmenu'
  },
*/
  /*'get /deletemenuapi/:menu':{
    controller: 'menuapi',
    action: 'deletemenuapi'
  },

  'get /item':{},

  /*pemesanan 
  'get /lihatpesanan':{
    controller:'pemesanan',
    action:'index'
  },
  'post /buatpesanan':{
    controller:'pemesanan',
    action:'buatpesanan'
  },
  'get /generatecode':{},
  'post /approvepesan':{},
 */

    /*presensi API route */
    'get /presensitambah/:id':{},
    'post /presensitambah/:username':{},
    'get /rekappresensi/:username':{},

    

};
