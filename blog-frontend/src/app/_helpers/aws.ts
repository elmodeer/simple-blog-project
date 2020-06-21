import { Observable } from 'rxjs';
import { HttpHeaders, HttpRequest, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';


@Injectable({
    providedIn: 'root'
})
export class AWSUtility {

    constructor(private http: HttpClient) { }

    uploadfileAWSS3(fileuploadurl: string, file: File): Observable<any>{ 
        //this will be used to upload all csv files to AWS S3
        // const headers = new HttpHeaders({'Content-Type': file.type});
        const req = new HttpRequest(
        'PUT',
        fileuploadurl,
        file,
        {
        reportProgress: true, // This is required for track upload process
        });
        return this.http.request(req);
    }
}
