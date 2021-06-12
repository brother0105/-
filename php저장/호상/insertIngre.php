<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받습니다.

        $ingre=$_POST['ingre'];
        $num=$_POST['num'];
        $date=$_POST['date'];

        if(empty($ingre)){
            $errMSG = "재료를 입력하세요.";
        }
        else if(empty($num)){
            $errMSG = "개수를 입력하세요.";
        }
        else if(empty($date)){
            $errMSG = "기한을 입력하세요.";
        }

        if(!isset($errMSG)) // 모두 입력이 되었다면 
        {
            try{
                // SQL문을 실행하여 데이터를 MySQL 서버의 ingretable 테이블에 저장합니다. 
                $stmt = $con->prepare('INSERT INTO ingretable(ingre, num, date) VALUES(:ingre, :num, :date)');
                $stmt->bindParam(':ingre', $ingre);
                $stmt->bindParam(':num', $num);
                $stmt->bindParam(':date', $date);

                if($stmt->execute())
                {
                    $successMSG = "새로운 사용자를 추가했습니다.";
                }
                else
                {
                    $errMSG = "사용자 추가 에러";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
        }

    }

?>


<?php 
    if (isset($errMSG)) echo $errMSG;
    if (isset($successMSG)) echo $successMSG;

	$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
   
    if( !$android )
    {
?>
    <html>
       <body>

            <form action="<?php $_PHP_SELF ?>" method="POST">
                Ingre: <input type = "text" name = "ingre" />
                Num: <input type = "text" name = "num" />
                Date: <input type = "text" name = "date" />
                <input type = "submit" name = "submit" />
            </form>
       
       </body>
    </html>

<?php 
    }
?>