package beaconmuseumweb

import (
    "net/http"
    "html/template"
    "regexp"
    "appengine"
    "appengine/datastore"
)

type BeaconUser struct {
    Name string
    ParseKey string
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
    q := datastore.NewQuery("User").Filter("Name =", museumName)

    results := make([]BeaconUser, 1)

    if _, err := q.GetAll(c, &results); err != nil {
        http.Error(w, err.Error(), http.StatusInternalServerError)
        return
    }
    if results[0].Name == "" {
        http.NotFound(w, r)
        return
    }
    renderTemplate(w, "home", &results[0])

}

func beaconmuseumKey(c appengine.Context) *datastore.Key {
    return datastore.NewKey(c, "BeaconMuseumWeb", "default_beaconmuseum", 0, nil)
}

