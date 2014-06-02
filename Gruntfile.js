module.exports = function(grunt) {
    "use strict";

    require('load-grunt-tasks')(grunt);

    grunt.registerTask('default', [
        'update_submodules',
        'stylus',
        'exec:bower',
        'exec:addPlatform',
        'exec:plugins_device',
        'exec:plugins_network',
        'exec:plugins_update',
        'exec:prepare',
        'exec:build',
        'exec:backend_npm',
        'build'
    ]);

    grunt.registerTask('update', [
        'exec:submodules',
        'exec:rmPlatform',
        'clean:plugins',
        'clean:assets_www',
        'clean:assets_backend',
        'default'
    ]);

    grunt.registerTask('build', [
        'compress:backend',
        'copy'
    ]);    

    grunt.registerTask('run', [
        'stylus',
        'exec:run'
    ]);     

    grunt.initConfig({

        pkg: grunt.file.readJSON('package.json'),
        exec:{
            addPlatform:{
                command:"../../node_modules/.bin/cordova platforms add android",
                stdout:true,
                stderror:true,
                cwd:"popcorn-mobile/frontend"
            },
            rmPlatform:{
                command:"../../node_modules/.bin/cordova platforms rm android",
                stdout:true,
                stderror:true,
                cwd:"popcorn-mobile/frontend"

            },
            prepare:{
                command:"../../node_modules/.bin/cordova prepare",
                stdout:true,
                stderror:true,
                cwd:"popcorn-mobile/frontend"

            },
            build:{
                command:"../../node_modules/.bin/cordova build android",
                stdout:true,
                stderror:true,
                cwd:"popcorn-mobile/frontend"

            },
            run:{
                command:"../../node_modules/.bin/cordova run android",
                stdout:true,
                stderror:true,
                cwd:"popcorn-mobile/frontend"

            },

            serve:{
                command:"../../node_modules/.bin/phonegap serve",
                stdout:true,
                stderror:true,
                cwd:"popcorn-mobile/frontend"

            },   

            plugins_device: {
                command:'../../node_modules/.bin/cordova plugin add org.apache.cordova.device',
                cwd:"popcorn-mobile/frontend"
            },

            plugins_network: {
                command:'../../node_modules/.bin/cordova plugin add org.apache.cordova.network-information',
                cwd:"popcorn-mobile/frontend"
            },

            plugins_update: {
                command:'../../node_modules/.bin/cordova plugin add https://github.com/popcorn-official/codova-update-plugin.git',
                cwd:"popcorn-mobile/frontend"
            },

            bower: {
                command:'../../node_modules/.bin/bower install',
                cwd:"popcorn-mobile/frontend"
            },

            backend_npm: {
                command:'npm install',
                cwd:"popcorn-mobile/backend"
            },

            submodules:'git submodule foreach git pull origin master'     
        },

        stylus: {
            compile: {
                options: {
                    compress: false,
                    'resolve url': true,
                    use: ['nib'],
                    paths: ['./popcorn-mobile/frontend/www/styl']
                },
                files: {
                    'popcorn-mobile/frontend/www/css/app.css' : 'popcorn-mobile/frontend/www/styl/app.styl'
                }
            }
        },

        copy: {
          main: {
            files: [
              //copy xml for plugins and project config
              {expand: true, cwd: 'popcorn-mobile/frontend/platforms/android/res/xml/', src: ['*.xml'], dest: 'app/res/xml/'},
              // copy assets
              {expand: true, cwd: 'popcorn-mobile/frontend/platforms/android/assets/www/', src: ['**'], dest: 'app/assets/www/'},
              // copy plugins
              {expand: true, cwd: 'popcorn-mobile/frontend/platforms/android/src/org/', src: ['**'], dest: 'app/src/org/'}
            ]
          }
        },

        clean: {
          plugins: ["popcorn-mobile/frontend/plugins/*", "!popcorn-mobile/frontend/plugins/.gitkeep"],
          assets_www: ["app/assets/www/*", "!app/assets/www/.gitkeep"],
          assets_backend: ["app/assets/*.zip"]
        },

        update_submodules: {
            default: {
                options: {}
            }
        },

        compress: {
          backend: {
            options: {
              archive: 'app/assets/backend.zip'
            },
            files: [
              {expand: true, cwd: 'popcorn-mobile/backend/', src: ['**'], dest: 'backend/'},
            ]
          }
        }  

    });

};