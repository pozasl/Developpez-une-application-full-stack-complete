/**
 * MDD OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
import { HttpHeaders }                                       from '@angular/common/http';

import { Observable }                                        from 'rxjs';

import { NewPost } from '../model/models';
import { NewReply } from '../model/models';
import { Post } from '../model/models';
import { ResponseMessage } from '../model/models';


import { Configuration }                                     from '../configuration';



export interface PostsServiceInterface {
    defaultHeaders: HttpHeaders;
    configuration: Configuration;

    /**
     * Add a new Post
     * 
     * @param newPost 
     */
    createPost(newPost?: NewPost, extraHttpRequestParams?: any): Observable<ResponseMessage>;

    /**
     * Post a new message
     * 
     * @param id 
     * @param newReply 
     */
    createReply(id: string, newReply: NewReply, extraHttpRequestParams?: any): Observable<ResponseMessage>;

    /**
     * Get a Post by its id
     * 
     * @param id 
     */
    getPostById(id: string, extraHttpRequestParams?: any): Observable<Post>;

}
