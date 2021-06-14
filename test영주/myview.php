<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');
        

    $stmt = $con->prepare('select * from recipe AS rec JOIN page_view AS pv ON rec.recipe_num=pv.recipe_num');
    $stmt->execute();

    if ($stmt->rowCount() > 0)
    {
        $data = array(); 

        while($row=$stmt->fetch(PDO::FETCH_ASSOC))
        {
            extract($row);
    
            array_push($data, 
                array(
				'recphoto'=>$photo,
                'recname'=>$recipe_name,
                'date'=>$view_day
            ));
        }

        header('Content-Type: application/json; charset=utf8');
        $json = json_encode(array("tester"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
        echo $json;
    }

?>