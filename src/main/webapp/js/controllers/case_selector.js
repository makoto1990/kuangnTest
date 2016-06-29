/**
 * Created by Weng on 2016/6/14.
 */


function CaseSelectorController($scope, $rootScope, $location, $cookieStore, $http, $log, $timeout,
                                i18nService, uiGridConstants, $loading){
    $scope.langs = i18nService.getAllLangs();
    $scope.lang = 'zh-cn';

    $scope.selectedRows = [];

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
