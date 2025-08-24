
import { Component, ElementRef, Renderer2 } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';


interface JovialData {
  resources?: any; 
}

@Component({
  selector: 'app-my-colony',
  templateUrl: './my-colony.component.html',
  styleUrl: './my-colony.component.css'
})

export class MyColonyComponent {
  constructor(
    private el: ElementRef, 
    private renderer: Renderer2,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  res: string = "";
  url: undefined | string; 
  //data: undefined | JovialData;
  presets: string[] = ["ping", "pong", "peng", "pang", "pung"];
  suggestions: string[] = [];



  async ngAfterViewInit(): Promise<void> {
    this.fillPre();
    console.log(this.presets);
  }


  async prev(rss: string): Promise<void> {
    const q = rss.toLowerCase();
    this.suggestions = this.presets.filter(item =>
      item.toLowerCase().includes(q),
    )
  }


  async search(rss: string): Promise<JovialData> {
    this.url = "https://mc1.my-colony.com/api.php?pf=1&g=1";
    let text = "";
    let color = "";
    const resolved = await this.resilientFetch(this.url);  // get the actual JovialData  
    //this.data = resolved;
    if (!resolved || Object.keys(resolved).length === 0) {
        text = "NOPE";
        color = 'red';

    } else if (rss == " " || rss == "") {
      text = "";
      color = 'black';

    } else {
      const len = resolved.resources.length;
      for(let i = 0; i < Math.ceil(resolved.resources.length/2); i++ ) {
        const top = resolved.resources[i];
        if(top.name == this.titleCase(this.res)) {
          text = JSON.stringify(top);
          color = 'black';
          break;        
        } 

        if(i == Math.ceil(len/2)-1) {
          text = "NOPE";
          color = 'red';
          break;
        } 

        const btm = resolved.resources[(len-1)-i];
        if (btm.name == this.titleCase(this.res)) {
          text = JSON.stringify(btm);
          color = 'black';
          break;
        } 
      }
    }

    const div = this.el.nativeElement.querySelector('#result');
    this.renderer.setProperty(div, 'textContent', text);
    this.renderer.setStyle(div, 'color', color);   
    console.log(resolved);
    return resolved;
  }


  async resilientFetch(url: string): Promise<JovialData> {
    try {
      const response = await fetch(url, { method: 'GET' });
      if (!response.ok) {
        throw new Error(`Harbinger of misfortune, our requests are thwarted: ${response.statusText}`);
      }
      const jsonData: JovialData = await response.json();
      return jsonData;
    } catch (error) {
      console.error(`Zounds! Our valiant attempt was met with defeat: `, error);
      throw error; // Ensure the calling function knows of our trials and tribulations
    }
  }

  titleCase(input: string): string {
  if (!input) return "";
  return input
    .split(" ")
    .map(word => word[0].toUpperCase() + word.slice(1).toLowerCase())
    .join(" "); 
  }

  async fillPre(): Promise<void> {
    const rss = '';
    this.presets = [];
    let data = await this.search(rss);
    let len = data.resources.length;
    console.log("Ping: " + len);
    
    for(let i = 0; i < Math.ceil(len/2); i++) {
      this.presets.push(data.resources[i].name);
      if(i != Math.ceil(len/2)-1) {
        this.presets.push(data.resources[len-1-i].name)
      }
    };
  }
}
