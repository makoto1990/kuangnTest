<%--
  Created by IntelliJ IDEA.
  User: qjb216
  Date: 16/6/3
  Time: 下午3:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Tree Multiselect test</title>

  <meta charset="UTF-8">

  <style>
    * {
      font-family: sans-serif;
    }
  </style>


  <!-- 新 Bootstrap 核心 CSS 文件 -->
  <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <link rel="stylesheet" href="../static/css/bootstrap-datetimepicker.min.css"  media="screen">
  <link rel="stylesheet" href="../static/css/jquery.tree-multiselect.min.css">

  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
  <![endif]-->

  <script type="text/javascript" src="../static/js/jquery/jquery-1.8.3.min.js" charset="UTF-8"></script>
  <script type="text/javascript" src="../static/js/bootstrap/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="../static/js/jquery-ui.min.js"></script>
  <script type="text/javascript" src="../static/js/jquery.tree-multiselect.js"></script>
</head>

<body>
<select id="test-select" multiple="multiple">
  <option value="blueberry" data-section="Smoothies">Blueberry</option>
  <option value="strawberry" data-section="Smoothies">Strawberries</option>
  <option value="peach" data-section="Smoothies">Peach</option>
  <option value="milk tea" data-section="Bubble Tea">Milk Tea</option>
  <option value="green apple" data-section="Bubble Tea">Green Apple</option>
  <option value="passion fruit" data-section="Bubble Tea" data-description="The greatest flavor" selected="selected">Passion Fruit</option>
  <option value="blueberry" data-section="Smoothies">Blueberry</option>
  <option value="strawberry" data-section="Smoothies">Strawberries</option>
  <option value="peach" data-section="Smoothies">Peach</option>
  <option value="milk tea" data-section="Bubble Tea">Milk Tea</option>
  <option value="green apple" data-section="Bubble Tea">Green Apple</option>
  <option value="passion fruit" data-section="Bubble Tea" data-description="The greatest flavor" selected="selected">Passion Fruit</option>
  <option value="blueberry" data-section="Smoothies">Blueberry</option>
  <option value="strawberry" data-section="Smoothies">Strawberries</option>
  <option value="peach" data-section="Smoothies">Peach</option>
  <option value="milk tea" data-section="Bubble Tea">Milk Tea</option>
  <option value="green apple" data-section="Bubble Tea">Green Apple</option>
  <option value="passion fruit" data-section="Bubble Tea" data-description="The greatest flavor" selected="selected">Passion Fruit</option>
  <option value="blueberry" data-section="Smoothies">Blueberry</option>
  <option value="strawberry" data-section="Smoothies">Strawberries</option>
  <option value="peach" data-section="Smoothies">Peach</option>
  <option value="milk tea" data-section="Bubble Tea">Milk Tea</option>
  <option value="green apple" data-section="Bubble Tea">Green Apple</option>
  <option value="passion fruit" data-section="Bubble Tea" data-description="The greatest flavor" selected="selected">Passion Fruit</option>


</select>



<script type="text/javascript">
  $("#test-select").treeMultiselect({ enableSelectAll: true, sortable: true });
</script>

<script type="text/javascript">
  $(document).ready(function(){
    $("button").click(function(){

    });
  });
</script>

<button>test</button>
</body>
</html>

