<?php

// a PHP client library for pubsubhubbub
// as defined at http://code.google.com/p/pubsubhubbub/
// written by Josh Fraser | joshfraser.com | josh@eventvue.com
// Released under Apache License 2.0

class Subscriber {
//    GOOGLE CODE
    
    // put your google key here
    // required if you want to use the google feed API to lookup RSS feeds
    protected $google_key = "";
    
    protected $hub_url;
    protected $callback_url;
    protected $credentials;
    // accepted values are "async" and "sync"
    protected $verify = "async"; 
    protected $verify_token;
    protected $lease_seconds;
    // create a new Subscriber (credentials added for SuperFeedr support)
    public function __construct($hub_url, $callback_url, $credentials = false) {

        if (!isset($hub_url))
            throw new Exception('Please specify a hub url');

        http://localhost/client/register.php?action=r&query=SELECT+%3Fsubject+%0D%0AWHERE+%7B%0D%0A++%3Fsubject+%3Chttp%3A%2F%2Fwww.dati.lombardia.it%2Fresource%2Fnf78-nj6b%2Fidsensore%3E+%222135%22.%0D%0A%7D&endpoint=http%3A%2F%2Flocalhost%2Fserver%2F

        if (!preg_match("|^https?://|i",$hub_url))
            throw new Exception('The specified hub url does not appear to be valid: '.$hub_url);
            
        if (!isset($callback_url))
            throw new Exception('Please specify a callback');



        $this->hub_url = $hub_url;
        $this->callback_url = $callback_url;
        $this->credentials = $credentials;
    }
    
    // $use_regexp lets you choose whether to use google AJAX feed api (faster, but cached) or a regexp to read from site
    public function find_feed($url, $http_function = false) {
        // using google feed API
        $url = "http://ajax.googleapis.com/ajax/services/feed/lookup?key={$this->google_key}&v=1.0&q=".urlencode($url);
        // fetch the content 
        if ($http_function)
            $response = $http_function($url);
        else
            $response = $this->http($url);

        $result = json_decode($response, true);
        $rss_url = $result['responseData']['url'];
        return $rss_url;
    }
    
    public function subscribe($topic_url, $http_function = false) {
        return $this->change_subscription("subscribe", $topic_url, $http_function = false);
    }
    
    public function unsubscribe($topic_url, $http_function = false) {
        return $this->change_subscription("unsubscribe", $topic_url, $http_function = false);
    }

    // helper function since sub/unsub are handled the same way
    private function change_subscription($mode, $topic_url, $http_function = false) {

        if (!isset($topic_url))
            throw new Exception('Please specify a topic url');
            
         // lightweight check that we're actually working w/ a valid url
         if (!preg_match("|^https?://|i",$topic_url)) 
            throw new Exception('The specified topic url does not appear to be valid: '.$topic_url);
        
        // set the mode subscribe/unsubscribe
        $post_string = "hub.mode=".$mode;
        $post_string .= "&hub.callback=".urlencode($this->callback_url);
        $post_string .= "&hub.verify=".$this->verify;
        $post_string .= "&hub.verify_token=".$this->verify_token;
        // Do not work properly with that parameter
        // $post_string .= "&hub.lease_seconds=".$this->lease_seconds;
    
        // append the topic url parameters
        $post_string .= "&hub.topic=".urlencode($topic_url);


        // make the http post request and return true/false
        // easy to over-write to use your own http function

        if ($http_function)
            return $http_function($this->hub_url,$post_string);
        else
            return $this->http($this->hub_url,$post_string);
    }
    
    // default http function that uses curl to post to the hub endpoint // TODO: HTTP POST Request
    private function http($url, $post_string) { // TODO: capire sintassi corretta
        echo $url;
        echo "<br>";
        echo $post_string;
        echo "<br>";
        // add any additional curl options here
        $options = array(CURLOPT_URL => $url,
                         CURLOPT_USERAGENT => "PubSubHubbub-Subscriber-PHP/1.0",
                         CURLOPT_RETURNTRANSFER => true);
                         
        if ($post_string) {
            $options[CURLOPT_POST] = true;
            $options[CURLOPT_POSTFIELDS] = $post_string;
        }
                         
        if ($this->credentials)
            $options[CURLOPT_USERPWD] = $this->credentials;

        $ch = curl_init();
        curl_setopt_array($ch, $options);

        $response = curl_exec($ch); //TODO: $response è false
        echo "Response ".(int)$response;
        echo "<br>";
        $info = curl_getinfo($ch);

//        DEBUG: STAMPA valori $info (cURL session parameters)
        for ($i = 0; $i <  count($info); $i++) {
            $key=key($info);
            $val=$info[$key];
            if ($val<> ' ') {
                if (is_array($val)) {
                    echo $key . " = ";
                    print_r(array_values($val));
                    echo "<br>";
                }
                else
                    echo $key ." = ".  $val ." <br> ";
            }
            next($info);
        }


        // all good -- anything in the 200 range 
        if (substr($info['http_code'],0,1) == "2") {
            return $response;
        }
        return false;   
    }
}


?>