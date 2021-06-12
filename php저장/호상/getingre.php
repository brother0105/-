<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');
        

    $stmt = $con->prepare('select * from ingretable');
    $stmt->execute();

    if ($stmt->rowCount() > 0)
    {
        $data = array(); 

        while($row=$stmt->fetch(PDO::FETCH_ASSOC))
        {
            extract($row);
    
            array_push($data, 
                array('id'=>$id,
                'ingre'=>$ingre,
                'num'=>$num,
                'date'=>$date
            ));
        }

        header('Content-Type: application/json; charset=utf8');
        $json = json_encode(array("tester"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
        echo $json;
    }

?>