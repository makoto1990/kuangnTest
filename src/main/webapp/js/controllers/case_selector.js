/**
 * Created by Weng on 2016/6/14.
 */


function CaseSelectorController($scope, $rootScope, $location, $cookieStore, $http, $log, $timeout,
                                i18nService, uiGridConstants, $loading){
    $scope.langs = i18nService.getAllLangs();
    $scope.lang = 'zh-cn';
    // $scope.gmtDate = null;
    $scope.selauth = null;
    $scope.sn = null;
    $scope.uuid = null;
    $scope.datetimeperiod = null;
    $scope.mprotocol = null;
    $scope.mmodule = null;
    $scope.datetime = null;
    $scope.datetimeperiodshow = false;
    $scope.enable = false;
    $scope.selectedRows = [];
    $scope.disVersion = [{"id":1000,"name":"trial"},{"id":1001,"name":"standard"}];

    $scope.gridOptions = {
        enableFiltering: true,
        enableRowSelection: true,
        enableSelectAll: true,
        selectionRowHeaderWidth: 35,
        rowHeight: 35,
        showGridFooter:true,
        enableColumnResizing: true,
        enableFullRowSelection: true,
        onRegisterApi: function(gridApi) { //register grid data first within the gridOptions
            $scope.gridApi = gridApi;
        }
    };

    $scope.updateSelectedRows = function() {
        $scope.selectedRows = $scope.gridApi.selection.getSelectedRows();
    };

    $scope.gridOptions.columnDefs = [
        // { name: 'id' },
        { name: 'name', displayName: '名称', width: '25%'},
        { name: 'type', displayName: '用例类型', width: '10%'},
        { name: 'deviceType', displayName: '设备类型', width: '10%'},
        { name: 'protocol', displayName: '协议', width: '10%'},
        { name: 'description', displayName: '描述', width: '40%'},
        // { name: 'age', displayName: 'Age (not focusable)', allowCellFocus : false },
        // { name: 'address.city' }
    ];

    $scope.gridOptions.multiSelect = true;

    $scope.info = {};

    $scope.toggleMultiSelect = function() {
        $scope.gridApi.selection.setMultiSelect(!$scope.gridApi.grid.options.multiSelect);
    };

    $scope.toggleModifierKeysToMultiSelect = function() {
        $scope.gridApi.selection.setModifierKeysToMultiSelect(!$scope.gridApi.grid.options.modifierKeysToMultiSelect);
    };

    $scope.selectAll = function() {
        $scope.gridApi.selection.selectAllRows();
    };

    $scope.clearAll = function() {
        $scope.gridApi.selection.clearSelectedRows();
    };

    $scope.toggleRow1 = function() {
        $scope.gridApi.selection.toggleRowSelection($scope.gridOptions.data[0]);
    };

    $scope.toggleFullRowSelection = function() {
        $scope.gridOptions.enableFullRowSelection = !$scope.gridOptions.enableFullRowSelection;
        $scope.gridApi.core.notifyDataChange( uiGridConstants.dataChange.OPTIONS);
    };

    $scope.setSelectable = function() {
        $scope.gridApi.selection.clearSelectedRows();

        $scope.gridOptions.isRowSelectable = function(row){
            return row.entity.age <= 30;
        };
        $scope.gridApi.core.notifyDataChange(uiGridConstants.dataChange.OPTIONS);

        $scope.gridOptions.data[0].age = 31;
        $scope.gridApi.core.notifyDataChange(uiGridConstants.dataChange.EDIT);
    };

    $scope.downloadSelected = function () {

        console.log($scope.gridOptions.data);
        $scope.updateSelectedRows();
        var jsonStringToBeSave = JSON.stringify($scope.selectedRows);

        var data = "{name: 'Bob', occupation: 'Plumber'}";
        var element = document.createElement('a');
        element.setAttribute('href', 'data:text/json;charset=utf-8,' + encodeURIComponent(jsonStringToBeSave));
        element.setAttribute('download', "testcase.json");

        element.style.display = 'none';
        document.body.appendChild(element);

        element.click();

        document.body.removeChild(element);
    };

    $scope.downloadSelectedfordes3 = function(){
        console.log($scope.gridOptions.data);
        $scope.updateSelectedRows();
        var jsonStringToBeSave = JSON.stringify($scope.selectedRows);

        $http({
            url:"rest/transcontenttestcase",
            method: "POST",
            data:{
                data: jsonStringToBeSave
            }
        })
            .success(function(data){
                    console.log(data);
                    if(data !="fail"){
                        var element = document.createElement('a');
                        element.setAttribute('href', 'des3/testcase.des3');
                        // element.setAttribute('href',  'testcase.des3');
                        element.setAttribute('download', "testcase.des3");

                        element.style.display = 'none';
                        document.body.appendChild(element);

                        element.click();

                        document.body.removeChild(element);
                    }else{
                        console.log(data);
                    }
                }
            )

            .error(function(err){
                console.log(err);
            })


    };

    // $scope.$watch("datetime", function( newVal, oldVal){
    //    if(newVal !=oldVal  && newVal){
    //        var choicedate = new Date(newVal);
    //        console.log(choicedate.getDate());
    //        console.log(choicedate.getFullYear());
    //        console.log(choicedate.getMonth());
    //        $scope.datetime = newVal;
    //        $scope.calDateTimePeriod();
    //    }
    // })

    $scope.calDateTimePeriod = function(){
        console.log($scope.datetime);
        if(angular.isUndefined($scope.datetime)){
            alert("Please pick a datetime")
        }else{
            var startDate = new Date();
            var endDate = new Date($scope.datetime);
            var divnum = 1000*3600*24;
            $scope.datetimeperiod = parseInt(endDate.getTime() - startDate.getTime())/divnum;
            if($scope.datetimeperiod < 0){
                alert("Please choice a date bigger than today, thank you!");
            }else{
                $scope.datetimeperiod = Math.ceil($scope.datetimeperiod).toString() +"d";
                $scope.datetimeperiodshow = true;
            }
        }
    };

    $scope.createBtnEnable = function(){
        $scope.enable = angular.isUndefined($scope.sn) || angular.isUndefined($scope.uuid) || angular.isUndefined($scope.selauth) || angular.isUndefined($scope.datetime) || angular.isUndefined($scope.datetimeperiod) || angular.isUndefined($scope.        $scope.enable = angular.isUndefined($scope.sn) || angular.isUndefined($scope.uuid) || angular.isUndefined($scope.selauth) || angular.isUndefined($scope.datetime) || angular.isUndefined($scope.datetimeperiod) || angular.isUndefined($scope.authorizedEdtion));

        console.log($scope.sn);
        console.log(angular.isUndefined($scope.sn))
        console.log("btn enable: " + $scope.enable.toString());
        if($scope.enable){
            return false;
        }else{
            return true;
        }
    };

    $scope.generateLicenseDes = function(){
        console.log($scope.authorizedEdtion.name);
        console.log({'sn':$scope.sn,'uuid':$scope.uuid, 'authorizedFor':$scope.selauth, "authorizedEdtion": "trial",'authorizedDate':$scope.datetime,'authorizedPeriod':$scope.datetimeperiod,'protocol':'all','module':'all'})
            $http({method: 'POST',
                url: 'rest/license',
                data: {
                    data: {'sn':$scope.sn,'uuid':$scope.uuid, 'authorizedFor':$scope.selauth, "authorizedEdtion": $scope.authorizedEdtion.name,'authorizedDate':$scope.datetime,'authorizedPeriod':$scope.datetimeperiod,'protocol':'all','module':'all'}
                    }
            })
                .success(function (data) {
                    console.log(data);
                    if(data.data !="fail"){
                        var element = document.createElement('a');
                        element.setAttribute('href', 'des3/License.des3');
                        // element.setAttribute('href',  'testcase.des3');
                        element.setAttribute('download', "License.des3");

                        element.style.display = 'none';
                        document.body.appendChild(element);

                        element.click();

                        document.body.removeChild(element);
                    }else{
                        console.log(data);
                    }
                })
                .error(function (data) {
                    console.log(data);
                })
    }

    $scope.gridOptions.onRegisterApi = function(gridApi){
        //set gridApi on scope
        $scope.gridApi = gridApi;
        gridApi.selection.on.rowSelectionChanged($scope,function(row){
            var msg = 'row selected ' + row.isSelected;
            $log.log(msg);
            $scope.updateSelectedRows();
        });

        gridApi.selection.on.rowSelectionChangedBatch($scope,function(rows){
            var msg = 'rows changed ' + rows.length;
            $log.log(msg);
            $scope.updateSelectedRows();
        });
    };

    /** -------------------------------      START CODE      ------------------------------- */
    $loading.start('grid_loading');
    $http.get('rest/git-repo') //?pull=false
        .success(function(data) {
            $scope.gridOptions.data = data;
            $loading.finish('grid_loading');
        });

};
