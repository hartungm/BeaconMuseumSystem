package beaconmuseumweb

import (
    "net/http"
    "html/template"
    "regexp"
    "appengine"
    "appengine/datastore"
    "fmt"
)

//the `datastore` declarations seem to be unnecessary
//datastore objects return as [{prop, prop...}]
//i don't like it, but we can work with it
//try GQL?

//i should also try to see if i can just get datastore to return key-value pairs
//otherwise, i can just build a json string and store that in the datastore instead
type BeaconUser struct {
    MuseumName string   `datastore:"MuseumName"`
    ParseKey string     `datastore:"ParseKey"`
}

var templates = template.Must(template.ParseFiles("template/home.html"))
var validPath = regexp.MustCompile("^/([a-zA-Z0-9]+)$")

func init() {
    http.HandleFunc("/", makeHandler(rootHandler))
}

func makeHandler(fn func (http.ResponseWriter, *http.Request, string)) http.HandlerFunc {
    return func(w http.ResponseWriter, r *http.Request) {
        //extract museum name here
        m := validPath.FindStringSubmatch(r.URL.Path)
        if m == nil {
            http.NotFound(w, r)
            return
        }
        fn(w,r,m[1])
    }
}

func renderTemplate(w http.ResponseWriter, tmpl string, u *BeaconUser) {
    err := templates.Execute(w, u)
    if err != nil {
        http.Error(w, err.Error(), http.StatusInternalServerError)
    }
}

func rootHandler(w http.ResponseWriter, r *http.Request, museumName string) {
    c := appengine.NewContext(r)
    //HACK
    //TODO insert datastore element here to see if that works
    //(here testing datastore access)
    //insertUser := BeaconUser {
    //    MuseumName: museumName,
    //    ParseKey:   "AnotherTestKey",
    //}
    //datastore.Put(c, datastore.NewIncompleteKey(c, "BeaconUser", nil), &insertUser)
    ///HACK

    //TODO i feel like we're doing the key wrong here...as in don't have one.  Figure it out
    //TODO ok, i finally got a query to work.  The hack isn't working because of the apply delay, which SHOULDN'T matter in production. the json is not what I expected, fix the json stuff and maybe it'll be better
    q := datastore.NewQuery("BeaconUser").KeysOnly()//.Filter("MuseumName =", museumName)

    results := make([]BeaconUser, 1)

    if _, err := q.GetAll(c, &results); err != nil {
        http.Error(w, err.Error(), http.StatusInternalServerError)
        return
    }
    fmt.Fprint(w, results)
    //if results[0].MuseumName == "" {
        //http.NotFound(w, r)
        //return
    //}
    //renderTemplate(w, "home", &results[0])

}

func beaconmuseumKey(c appengine.Context) *datastore.Key {
    return datastore.NewKey(c, "BeaconMuseumWeb", "default_beaconmuseum", 0, nil)
}

