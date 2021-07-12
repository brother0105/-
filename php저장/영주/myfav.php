<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');
        

    $stmt = $con->prepare('select * from recipe AS rec JOIN recipe_favorite AS rfav ON rec.recipe_num=rfav.recipe_num');
    $stmt->execute();

    if ($stmt->rowCount() > 0)
    {
        $data = array(); 

        while($row=$stmt->fetch(PDO::FETCH_ASSOC))
        {
            extract($row);
    
            array_push($data, 
                array(
                'recname'=>$recipe_name,
                'memo'=>$f_memo,
				'recphoto'=>$photo,
				'number'=>$recipe_num
            ));
        }

        header('Content-Type: application/json; charset=utf8');
        $json = json_encode(array("tester"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
        echo $json;
    }

?>